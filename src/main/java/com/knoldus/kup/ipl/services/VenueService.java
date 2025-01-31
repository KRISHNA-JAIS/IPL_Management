package com.knoldus.kup.ipl.services;

import com.knoldus.kup.ipl.models.Venue;
import com.knoldus.kup.ipl.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue saveVenue(Venue venue){
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenues(){
       return  (List<Venue>) venueRepository.findAll();
    }
}

