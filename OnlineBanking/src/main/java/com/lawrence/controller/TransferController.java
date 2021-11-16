package com.lawrence.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lawrence.dto.RecipientDto;
import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.Recipient;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.User;
import com.lawrence.service.TransactionService;
import com.lawrence.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    static final String RECIPIENTLIST = "recipientList";

    static final String RECIPIENT = "recipient";

    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model) {

        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

    @PostMapping("/betweenAccounts")
    public String betweenAccountsPost(@ModelAttribute("transferFrom") String transferFrom, @ModelAttribute("transferTo") String transferTo, @ModelAttribute("amount") String amount, Principal principal) throws Exception {
       
        User user = userService.findByUsername(principal.getName());
        
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();
        
        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);

        return "redirect:/userFront";
    }

    @GetMapping("/recipient")
    public String recipient(Model model, Principal principal) {
        
        List<Recipient> recipientList = transactionService.findRecipientList(principal);

        Recipient recipient = new Recipient();

        model.addAttribute(RECIPIENTLIST, recipientList);
        model.addAttribute(RECIPIENT, recipient);

        return RECIPIENT;
    }

    @PostMapping("/recipient/save")
    public String recipientPost(@ModelAttribute(RECIPIENT) RecipientDto recipientDto, Principal principal) {

        Recipient recipient = new Recipient();

        // Coping all Attributes from recipientDto to recipient where Attributes Names and Type matches
        BeanUtils.copyProperties(recipientDto, recipient);

        User user = userService.findByUsername(principal.getName());
        recipient.setUser(user);
        
        transactionService.saveRecipient(recipient);

        return "redirect:/transfer/recipient";
    }

    @GetMapping("/recipient/edit")
    public String recipientEdit(@RequestParam("recipientName") String recipientName, Model model, Principal principal) {

        Recipient recipient = transactionService.findRecipientByName(recipientName);
        
        List<Recipient> recipientList = transactionService.findRecipientList(principal);

        model.addAttribute(RECIPIENTLIST, recipientList);
        model.addAttribute(RECIPIENT, recipient);

        return RECIPIENT;
    }

    @GetMapping("/recipient/delete")
    @Transactional
    public String recipientDelete(@RequestParam("recipientName") String recipientName, Model model, Principal principal) {

        transactionService.deleteRecipientByName(recipientName);

        List<Recipient> recipientList = transactionService.findRecipientList(principal);

        Recipient recipient = new Recipient();
        model.addAttribute(RECIPIENT, recipient);
        model.addAttribute(RECIPIENTLIST, recipientList);


        return RECIPIENT;
    }

    @GetMapping("/toSomeoneElse")
    public String toSomeoneElse(Model model, Principal principal) {
        
        List<Recipient> recipientList = transactionService.findRecipientList(principal);

        model.addAttribute(RECIPIENTLIST, recipientList);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @PostMapping("/toSomeoneElse")
    public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal) {
        
        User user = userService.findByUsername(principal.getName());
        Recipient recipient = transactionService.findRecipientByName(recipientName);
        
        transactionService.toSomeoneElseTransfer(recipient, accountType, amount, user.getPrimaryAccount(), user.getSavingsAccount());

        return "redirect:/userFront";
    }
}
