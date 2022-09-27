package com.trader.exchangeservice;

import com.trader.exchangeservice.Model.Currency;
import com.trader.exchangeservice.Repository.CurrencyRepository;
import com.trader.exchangeservice.Repository.TransactionApproveQueueRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class ExchangeServiceApplication {

    private final CurrencyRepository currencyRepository;
    private final TransactionApproveQueueRepository transactionApproveQueueRepository;

    public ExchangeServiceApplication(CurrencyRepository currencyRepository, TransactionApproveQueueRepository transactionApproveQueueRepository) {
        this.currencyRepository = currencyRepository;
        this.transactionApproveQueueRepository = transactionApproveQueueRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExchangeServiceApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }


    // Her 12 saatte bir queue ya alınmış ve timeout süresi geçmiş transaction varsa siler.
    @Scheduled(fixedRate = (43200000))
    public void scheduledDeleteTransactionQueueTask() {

        transactionApproveQueueRepository.deleteTimoutQueueElements();
    }


    // Dakikada bir kurları günceller
    @Scheduled(fixedRate = 60000)
    public void scheduledCurrencyTask() {
        System.out.println(
                "Currency Refreshed - " );

        currencyRepository.deleteAll();
        currencyRepository.tableConfig();



        currencyRepository.save(new Currency(Currency.Type.USD, Currency.Type.TRY,
                BigDecimal.valueOf(generate(18,19)),BigDecimal.valueOf(generate(17,18)),LocalDateTime.now(clock())));


        currencyRepository.save(new Currency(Currency.Type.EUR, Currency.Type.TRY,
                BigDecimal.valueOf(generate(17.5,18)),BigDecimal.valueOf(generate(16.5,17.5)),LocalDateTime.now(clock())));


        currencyRepository.save(new Currency(Currency.Type.GOLD, Currency.Type.TRY,
                BigDecimal.valueOf(generate(970,980)),BigDecimal.valueOf(generate(960,970)),LocalDateTime.now(clock())));


        currencyRepository.save(new Currency(Currency.Type.EUR, Currency.Type.GOLD,
                BigDecimal.valueOf(generate(0.0181,0.02)),BigDecimal.valueOf(generate(0.016,0.181)),LocalDateTime.now(clock())));



        currencyRepository.save(new Currency(Currency.Type.EUR, Currency.Type.USD,
                BigDecimal.valueOf(generate(0.8,1)),BigDecimal.valueOf(generate(1.1,1.3)),LocalDateTime.now(clock())));



        currencyRepository.save(new Currency(Currency.Type.USD, Currency.Type.GOLD,
                BigDecimal.valueOf(generate(0.0192,0.0208)),BigDecimal.valueOf(generate(0.0181,0.192)),LocalDateTime.now(clock())));

        }


    private double generate(double min,double max){
        return ((double) (int) (new Random().nextDouble(min,max)*1000000))/1000000;
    }

}
