package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//@Api(value = "Trader", produces = PageAttributes.MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/trader")
public class TraderAccountController {

    private TraderAccountService traderAccountService;
    @Autowired
    public TraderAccountController(TraderAccountService traderAccountService){
        this.traderAccountService = traderAccountService;

    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(
            path = "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public TraderAccountView createTrader(@PathVariable String firstname, @PathVariable String lastname, @PathVariable @DateTimeFormat(pattern = "yyyy-mm-dd")LocalDate dob,
                                          @PathVariable String country, @PathVariable String email){
        try{
            Trader trader = new Trader();
            trader.setCountry(country);
            trader.setDob(dob);
            trader.setEmail(email);
            trader.setFirst_name(firstname);
            trader.setLast_name(lastname);
            return traderAccountService.createTraderAndAccount(trader);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public TraderAccountView createTrader(@RequestBody Trader trader){
        try{
            return traderAccountService.createTraderAndAccount(trader);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @DeleteMapping(path = "/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderId){
        try{
            traderAccountService.deleteTraderById(traderId);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping(path = "/withdrwa/traderId/{traderId}/amount/{amount}")
    public Account depositFund(@PathVariable Integer traderId, @PathVariable Double amount){
        try{
            return traderAccountService.deposit(traderId,amount);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping(path = "/withdrwa/traderId/{traderId}/amount/{amount}")
    public Account withdrawFund(@PathVariable Integer traderId, @PathVariable Double amount){
        try{
            return traderAccountService.withdraw(traderId,amount);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }




}
