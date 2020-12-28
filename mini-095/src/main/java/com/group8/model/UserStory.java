package com.group8.model;
 
import com.group8.model.User;
 
import java.time.LocalDate;
import java.util.ArrayList;
import org.bson.types.ObjectId;
 
public class UserStory extends Activity {
 
    // private String acceptanceCriteria;
 
    public UserStory() {
        // super();
    }
 
    // public UserStory (ObjectId projectId, String name, String description, LocalDate startDate, LocalDate endDate, String priority,String type, String acceptanceCriteria) {
    //     super(projectId, name, description, startDate, endDate, priority, type);
    //     this.acceptanceCriteria = acceptanceCriteria;
        
    // }
 
    // public UserStory (ObjectId projectId, String name, String description, LocalDate startDate, LocalDate endDate, String priority,String type) {
    //     super(projectId, name, description, startDate, endDate, priority, type);
        
    // }
    public UserStory (String projectName, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {

        super(projectName, name, description, startDate, endDate, priority, type);

    }
    // public String getAcceptanceCriteria() {
    //     return acceptanceCriteria;
    // }
 
    // public void setAcceptanceCriteria(String acceptanceCriteria) {
    //     this.acceptanceCriteria = acceptanceCriteria;
    // }
 
    // @Override
    // public String toString() {
    //     return "ID: " + this.getId() +  " " + this.getName() +
    //     " : " + this.getDescription() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
    //      "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() +  " AcceptanceCriteria: " + this.acceptanceCriteria ;
 
    // }
 
    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getDescription() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() ;
 
    }
}
 

