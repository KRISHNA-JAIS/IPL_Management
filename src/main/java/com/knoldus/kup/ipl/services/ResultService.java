package com.knoldus.kup.ipl.services;

import com.knoldus.kup.ipl.models.Match;
import com.knoldus.kup.ipl.models.Team;
import com.knoldus.kup.ipl.models.Venue;
import com.knoldus.kup.ipl.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ResultService {
    @Autowired
    MatchRepository matchRepository;
    
    @Autowired
    MatchService matchService;
    
    @Autowired
    TeamService teamService;
    
    @Autowired
    VenueService venueService;
    
//    @Autowired
//    private ProducerService kafkaService;
    
    public void getResult(Match match)
    {
        int team1Runs = Integer.parseInt(match.getTeam1Score());
        int team2Runs = Integer.parseInt(match.getTeam2Score());
        int team1Wickets = Integer.parseInt(match.getTeam1Wickets());
        int team2Wickets = Integer.parseInt(match.getTeam2Wickets());
        
        String result;
        String teamWinner;
        Team tossWinner = match.getTossWinnerTeam();
        String tossChoice = match.getTossChoice();
        Long firstInningsTeam = 1L;

//        if (match.getTeam1Over()*36 < team1Runs )
        if(tossWinner.getId().equals(match.getTeam1().getId())){
            if(tossChoice.equals("batting")){
                firstInningsTeam = match.getTeam1().getId();
            }else{
                firstInningsTeam = match.getTeam2().getId();
            }
        }else if(tossWinner.getId().equals(match.getTeam2().getId())){
            if(tossChoice.equals("batting")){
                firstInningsTeam = match.getTeam2().getId();
            }else{
                firstInningsTeam = match.getTeam1().getId();
            }
        }
        
        if(team1Runs>team2Runs){
            teamWinner = match.getTeam1().getName();
            if(firstInningsTeam.equals(match.getTeam1().getId())){         //won by runs
                result = teamWinner+" won by "+(team1Runs-team2Runs)+" runs";
            }else { //won by wickets
                result = teamWinner+" won by "+(10-team1Wickets)+" wickets";
            }
        }
        else {
            System.out.println("team2"+tossChoice);
            teamWinner = match.getTeam2().getName();
            if(firstInningsTeam.equals(match.getTeam2().getId())){       //won by runs
                result = teamWinner+" won by "+(team2Runs-team1Runs)+" runs";
            }else { //won by wickets
                result=teamWinner+" won by "+(10-team2Wickets)+" wickets";
            }
        }
        
        match.setMatchWinner(teamWinner);
        match.setResult(result);
        matchRepository.save(match);
    }
    
    //    Created Now  .
    
    public Model addScores(long match_id, Model model) {
        
        Match match = matchService.getMatchById(match_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + match_id));
        System.out.println("result add method"+match.getTeam1().getName()+match.getTeam1Over().equals("Yet to be played"));
        
        if (match.getTeam1Over().equals("Yet to be played")){
            match.setTeam1Over("");
            match.setTeam2Over("");
        }
        
        
        model.addAttribute("match",match);
        
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams",teams);
        
        List<Venue> venues= venueService.getAllVenues();
        model.addAttribute("venues",venues);
        
        return model;
        
        
    }
    
    public Model editScores(long match_id, Model model) {
        
        Match match = matchService.getMatchById(match_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + match_id));
        
        model.addAttribute("match",match);
        
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams",teams);
        
        List<Venue> venues= venueService.getAllVenues();
        model.addAttribute("venues",venues);
        model.addAttribute("team1", match.getTeam1().getName());
        model.addAttribute("team2", match.getTeam2().getName());
        
        return model;
        
    }
}
