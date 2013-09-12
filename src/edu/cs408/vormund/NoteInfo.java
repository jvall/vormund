package edu.cs408.vormund;

public class NoteInfo {
    private String name;
    private StringBuffer note;

    public NoteInfo(String nm, String nt) {
        this.name = nm;
        this.note = new StringBuffer(nt);
    }

    public NoteInfo(String nm, StringBuffer nt) {
        this.name = nm;
        this.note = nt;
    }

    public NoteInfo(NoteInfo ni) {
        this.name = ni.name;
        this.note = ni.note;
    }

    public String getName() {
        return name;
    }

    public StringBuffer getNote() {
        return note;
    }

    public static NoteInfo serializeCSVDump(String val) {
        String split[] = val.split(";");
        return new NoteInfo(split[0], split[1]);
    }
}

