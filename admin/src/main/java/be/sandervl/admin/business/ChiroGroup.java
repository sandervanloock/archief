package be.sandervl.admin.business;

public enum ChiroGroup {
    SPEELCLUB("52on50vspdubfmmbdenbamr0k0@group.calendar.google.com"),
    RAKKERS("ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com"),
    TOPPERS("80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com"),
    KERELS("vtuadhst93b1h7879gogidqepk@group.calendar.google.com"),
    ASPIRANTEN("25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com"),
    LEIDING("");

    private String calendarId;

    ChiroGroup(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

}
