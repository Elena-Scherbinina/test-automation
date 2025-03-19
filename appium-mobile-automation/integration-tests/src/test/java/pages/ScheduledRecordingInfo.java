package pages;

public class ScheduledRecordingInfo {
    String recordingAttributes = "";
    String recordingTitle = "";
    int id;

    public ScheduledRecordingInfo(String attributes, String title, int recId) {
        recordingAttributes = attributes;
        recordingTitle = title;
        id = recId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return recordingTitle;
    }

    public String getAttributes() {
        return recordingAttributes;
    }
}
