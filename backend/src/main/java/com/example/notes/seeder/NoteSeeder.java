package com.example.notes.seeder;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// Class for creating initial notes
@Component
public class NoteSeeder {

    @Autowired
    private NoteRepository noteRepository;

    @PostConstruct
    public void init() {
        deleteSeedData();
        createInitialNotes();
    }

    // Every time we run the app we must first delete the data from the previous runtime
    public void deleteSeedData() {
        noteRepository.deleteAll();
    }

    public void createInitialNotes() {
        noteRepository.saveAll(Arrays.asList(
                new Note("Meeting with John", "We need to discuss next phases of the product development. Testing is priority."),
                new Note("Dentist appointment", "Surgeon needs to fix couple of teeth."),
                new Note("Date with Mariah", "Mariah invited me to the restaurant near my location. I'm so excited to meet her!"),
                new Note("Grocery shopping", "Buy milk, eggs, bread, and some fresh fruits for the week."),
                new Note("Weekly team sync", "Discuss sprint progress, blockers, and next steps with the team."),
                new Note("Workout session", "Attend gym class at 6 PM. Focus on legs and cardio."),
                new Note("Call with mom", "Mom wants to chat about the weekend plans and family updates."),
                new Note("Project deadline", "Submit the final draft of the client presentation by Friday."),
                new Note("Car maintenance", "Visit the service center for oil change and tire checkup."),
                new Note("Dinner with Sarah", "Meeting Sarah at her favorite Italian restaurant at 7 PM."),
                new Note("Code review", "Review pull requests submitted by the junior developers."),
                new Note("Library visit", "Return borrowed books and pick up new ones for the month."),
                new Note("Plan vacation", "Research flights and accommodations for the trip to Greece."),
                new Note("Doctor appointment", "Annual health check-up scheduled at 9 AM."),
                new Note("Client meeting", "Prepare slides and documents for the meeting with Acme Corp."),
                new Note("Gardening", "Plant new flowers and prune the existing shrubs in the backyard."),
                new Note("Yoga class", "Attend yoga session to relax and improve flexibility."),
                new Note("Movie night", "Watch the latest blockbuster movie with friends at the theater."),
                new Note("Online course", "Complete the next module in the React development course."),
                new Note("Birthday gift for Alex", "Buy a thoughtful gift for Alex's birthday party this weekend."),
                new Note("Finance review", "Analyze monthly expenses and plan next month's budget."),
                new Note("Bake cookies", "Try the new chocolate chip cookie recipe."),
                new Note("Tech meetup", "Attend the local tech community event at the coworking space."),
                new Note("Team lunch", "Join the team for lunch at the new Mexican restaurant downtown."),
                new Note("Photography session", "Capture sunset photos at the nearby park."),
                new Note("Volunteer activity", "Help organize the charity event at the community center."),
                new Note("Weekend hike", "Explore the Blue Ridge hiking trail with friends."),
                new Note("Book reading", "Start reading 'The Alchemist' by Paulo Coelho."),
                new Note("Meditation practice", "Spend 15 minutes meditating to clear the mind."),
                new Note("Laundry", "Wash and iron clothes for the upcoming week."),
                new Note("Networking event", "Meet new professionals at the tech industry mixer.")
        ));
    }
}
