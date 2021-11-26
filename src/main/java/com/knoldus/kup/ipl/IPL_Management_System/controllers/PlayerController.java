package com.knoldus.kup.ipl.IPL_Management_System.controllers;

import com.knoldus.kup.ipl.IPL_Management_System.dao.PlayerDao;
import com.knoldus.kup.ipl.IPL_Management_System.dao.TeamDao;
import com.knoldus.kup.ipl.IPL_Management_System.models.Player;

import com.knoldus.kup.ipl.IPL_Management_System.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TeamDao teamDao;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    private Team team;


    @GetMapping("/addForm")
    public String addForm(Model model){
        Player player = new Player();
        model.addAttribute("player",player);
        return "addPlayer";
    }

    @PostMapping("/add")
    public String addPlayer(Player player){
        playerDao.save(player);
        return "redirect:/ipl";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Player player = playerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));

        model.addAttribute("player", player);
        return "update-player";
    }

    @PostMapping("/update/{id}")
    public String updatePlayer(@PathVariable("id") long id, Player player, Model model) {
//        if (result.hasErrors()) {
//            user.setId(id);
//            return "update-user";
//        }

        playerDao.save(player);
        return "redirect:/ipl";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable("id") long id, Model model) {
        Player player = playerDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));
        playerDao.delete(player);
        return "redirect:/ipl";
    }

}
