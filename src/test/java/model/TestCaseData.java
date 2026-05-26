package model;
public class TestCaseData
{
    private String module;
    private String testCaseTitle;
    private String expectedResult;
    private String priority;
    private String label;
    private String tcId;

    public String getTcId()
    {
        return tcId;
    }

    public void setTcId(String tcId)
    {
        this.tcId = tcId;
    }
    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public String getTestCaseTitle()
    {
        return testCaseTitle;
    }

    public void setTestCaseTitle(String testCaseTitle)
    {
        this.testCaseTitle = testCaseTitle;
    }

    public String getExpectedResult()
    {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult)
    {
        this.expectedResult = expectedResult;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return tcId + " || " +
                module + " || " +
                testCaseTitle + " || " +
                expectedResult + " || " +
                priority + " || " +
                label;
    }
}