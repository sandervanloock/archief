package be.sandervl.admin.business;

public enum ChiroGroup {
    SPEELCLUB("ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com"),
    RAKKERS("80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com"),
    TOPPERS("vtuadhst93b1h7879gogidqepk@group.calendar.google.com"),
    KERELS("25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com"),
    ASPIRANTEN("9aomc83dom4mbdq10m5t8vnkpo@group.calendar.google.com"),
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
