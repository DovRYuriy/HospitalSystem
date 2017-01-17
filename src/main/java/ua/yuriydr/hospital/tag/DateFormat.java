package ua.yuriydr.hospital.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * This class represents custom tag that is used to
 * format the date depending on local attribute.
 */
public class DateFormat extends SimpleTagSupport {

    private Timestamp date;
    private String locale;

    public DateFormat() {

    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }


    /**
     * Formats a date depending on locale attribute and return nothing.
     */
    @Override
    public void doTag() throws JspException, IOException {

        DateTimeFormatter dateTimeFormatter;

        switch (locale) {
            case "en":
                dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                break;
            case "ru":
            case "uk":
                dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                break;
            default:
                dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                break;
        }
        getJspContext().getOut().write(dateTimeFormatter.format(date.toLocalDateTime()));
    }

}
