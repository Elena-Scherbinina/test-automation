package pages;

/**
 * Represents information about a recorded TV program.
 */
public class RecordingInfo {

    private String recordingAttributes;
    private String recordingChannel;
    private String recordingDate;
    private String recordingTitle;
    private int id; // Recording identifier

    /**
     * Constructs a RecordingInfo object with the specified details.
     *
     * @param channel    The channel on which the recording was made.
     * @param date       The date of the recording.
     * @param title      The title of the recorded program.
     * @param recId      The unique identifier for the recording.
     * @param attributes Additional attributes related to the recording.
     */
    public RecordingInfo(String channel, String date, String title, int recId, String attributes) {
        this.recordingChannel = channel;
        this.recordingDate = date;
        this.recordingTitle = title;
        this.id = recId;
        this.recordingAttributes = attributes;
    }

    /**
     * Gets the recording ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title of the recording.
     */
    public String getTitle() {
        return recordingTitle;
    }

    /**
     * Gets the attributes of the recording.
     */
    public String getAttributes() {
        return recordingAttributes;
    }

    /**
     * Gets the date of the recording.
     */
    public String getDate() {
        return recordingDate;
    }

    /**
     * Gets the channel of the recording.
     */
    public String getChannel() {
        return recordingChannel;
    }
}
