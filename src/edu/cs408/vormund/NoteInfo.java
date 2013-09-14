package edu.cs408.vormund;

public class NoteInfo {
    private String name;
    private StringBuffer note;
    private int recordID;

    public NoteInfo(String nm, String nt, int recordID) {
        this.name = nm;
        this.note = new StringBuffer(nt);
        this.recordID = recordID;
    }

    public NoteInfo(String nm, StringBuffer nt, int recordID) {
        this.name = nm;
        this.note = nt;
        this.recordID = recordID;
    }

    public NoteInfo(NoteInfo ni) {
        this.name = ni.name;
        this.note = ni.note;
        this.recordID = ni.recordID;
    }

    public String getName() {
        return name;
    }

    public StringBuffer getNote() {
        return note;
    }

    public String toString() {
        return name + ";" + note.toString();
    }

    public static NoteInfo serializeCSVDump(String val, int recordID) {
        String split[] = val.split(";");
        return new NoteInfo(split[0], split[1], recordID);
    }
}

