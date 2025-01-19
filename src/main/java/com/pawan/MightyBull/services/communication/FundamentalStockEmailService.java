package com.pawan.MightyBull.services.communication;

import com.pawan.MightyBull.constants.AppConstant;
import com.pawan.MightyBull.dto.BaseDto;
import com.pawan.MightyBull.dto.Screener.ScreenerStockDetails;
import com.pawan.MightyBull.dto.communication.EmailTemplateDto;
import com.pawan.MightyBull.dto.communication.FundamentalStockEmailDto;
import com.pawan.MightyBull.utils.TemplateReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pawan Saini
 * Created on 17/01/25.
 */
@Slf4j
@Service
public class FundamentalStockEmailService extends EmailService {

    @Override
    protected EmailTemplateDto getEmailTemplateDTO(BaseDto baseDto) {
        FundamentalStockEmailDto emailDto = (FundamentalStockEmailDto) baseDto;

        String template = TemplateReader.readTemplate(AppConstant.Email.STOCK_RECOMMENDATION_TEMPLATE);
        String screenerUrl = AppConstant.SCREENER_URL;
        String stocks = "<div class=\"stock-card\">\n" +
                "                <div class=\"stock-title\">%s</div>\n" +
                "                <div class=\"stock-details\">\n" +
                "                    Recommendation: <span class=\"recommendation\">Buy</span><br>\n" +
                "                    Target Price: <b>%s Rs</b><br>\n" +
                "                    Current Price: <b>%s Rs</b><br>\n" +
                "                    PE: %s\n" +
                "                </div>\n" +
                "                <a href=\"https://www.screener.in/company/%s\" class=\"button\">View Details</a>\n" +
                "            </div>";
        String stockCards = emailDto.getStocks().stream()
                .map(stock -> {
                    return String.format(stocks, stock.getName(), stock.getHigh(), stock.getCurrentPrice(), stock.getStockPE(), stock.getStockId());
                })
                .collect(Collectors.joining(" "));
        return EmailTemplateDto.builder()
                .to(Arrays.asList("pawan.saini@moveinsync.com"))
                .subject("Stock Recommendation Alert From MightyBull")
                .template(template.replace("${stockCards}", stockCards))
                .build();
    }
}
