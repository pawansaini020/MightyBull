package com.pawan.MightyBull.configurations;

import com.pawan.MightyBull.utils.GsonUtils;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.SSLException;
import java.time.Duration;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${web.client.response.size:16777216}")
    private Integer webClientResponseSize;

    @Value("${web.client.connection.timeout:60000}")
    private Integer webClientConnectionTimeout;

    @Value("${web.client.read.timeout:60000}")
    private Integer webClientReadTimeout;

    @Value("${web.client.write.timeout:60000}")
    private Integer webClientWriteTimeout;

    @Value("${web.client.socket.timeout:60000}")
    private Integer webClientSocketTimeout;

    @Value("${web.client.max.connection:100}")
    private Integer webClientMaxConnection;

    @Value("${web.client.max.ideal.timeout:20000}")
    private Integer webClientMaxIdealTimeout;

    @Value("${web.client.max.life.timeout:60000}")
    private Integer webClientMaxLifeTimeout;

    @Value("${web.client.acquire.ideal.timeout:60000}")
    private Integer webClientAcquireIdealTimeout;

    @Value("${web.client.evict.life.timeout:120000}")
    private Integer webClientEvictLifeTimeout;

    @Bean
    public WebClient getWebClient() throws SSLException {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(webClientResponseSize))
                .build();
        SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        ConnectionProvider provider = ConnectionProvider.builder("fixed")
                .maxConnections(webClientMaxConnection)
                .maxIdleTime(Duration.ofMillis(webClientMaxIdealTimeout))
                .maxLifeTime(Duration.ofMillis(webClientMaxLifeTimeout))
                .pendingAcquireTimeout(Duration.ofMinutes(webClientAcquireIdealTimeout))
                .evictInBackground(Duration.ofMillis(webClientEvictLifeTimeout)).build();
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider)
                        .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext))
                        .tcpConfiguration(tcpClient -> tcpClient
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientConnectionTimeout)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(webClientReadTimeout))
                                        .addHandlerLast(new WriteTimeoutHandler(webClientWriteTimeout))
                                        .addHandlerLast(new IdleStateHandler(webClientMaxLifeTimeout, webClientMaxLifeTimeout, webClientMaxLifeTimeout))
                                ))
                        .responseTimeout(Duration.ofMillis(webClientSocketTimeout))
                ))
                .exchangeStrategies(strategies)
                .filter(ExchangeFilterFunction.ofRequestProcessor(request -> {
                    log.info("RestAPI request sent to: for method={} :: url={}, body: {}", request.method(), request.url(), GsonUtils.getGson().toJson(request.body()));
                    return Mono.just(request);
                }))
                .build();
    }
}
