package m.derakhshan.travelinkapp.RecyclerVIew;

public class Ticket_Model {

   private String title, question, status , answer;

    public Ticket_Model(String title,String question,String status,String answer){

        this.title=title;
        this.answer=answer;
        this.question=question;
        this.status= status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
