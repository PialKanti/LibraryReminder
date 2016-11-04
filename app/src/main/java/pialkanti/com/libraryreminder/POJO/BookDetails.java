package pialkanti.com.libraryreminder.POJO;

/**
 * Created by Pial on 10/21/2016.
 */
public class BookDetails {
    String BookTitle,Call_NO,Due_Date,Renewal;

    public BookDetails(String bookTitle, String call_NO, String due_Date, String renewal) {
        BookTitle = bookTitle;
        Call_NO = call_NO;
        Due_Date = due_Date;
        Renewal = renewal;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getCall_NO() {
        return Call_NO;
    }

    public void setCall_NO(String call_NO) {
        Call_NO = call_NO;
    }

    public String getDue_Date() {
        return Due_Date;
    }

    public void setDue_Date(String due_Date) {
        Due_Date = due_Date;
    }

    public String getRenewal() {
        return Renewal;
    }

    public void setRenewal(String renewal) {
        Renewal = renewal;
    }
}
