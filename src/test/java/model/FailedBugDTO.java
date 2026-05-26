package model;

public class FailedBugDTO
{
    private String recordNo;
    private String title;
    private String imagePath;
    private String section;
    private String priority;
    private String label;
    private String descriptions;

    public String getDescriptions()
    {
        return descriptions;
    }

    public void setDescriptions(String descriptions)
    {
        this.descriptions = descriptions;
    }

    public String getRecordNo() { return recordNo; }
    public void setRecordNo(String recordNo) { this.recordNo = recordNo; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}