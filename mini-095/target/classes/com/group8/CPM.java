public class CPM {
    // Should use json tree to demostrate CPM structure
    private WBS wbs;
    private String[] upStreamWBSs;
    private String[] downStreamWBSs;

    public CPM(WBS wbs, String[] upStreamWBSs, String[] downStreamWBSs) {
        this.wbs = wbs;
        this.upStreamWBSs = upStreamWBSs;
        this.downStreamWBSs = downStreamWBSs;
    }

    public WBS getWbs() {
        return wbs;
    }

    public void setWbs(WBS wbs) {
        this.wbs = wbs;
    }

    public String[] getUpStreamWBSs() {
        return upStreamWBSs;
    }

    public void setUpStreamWBSs(String[] upStreamWBSs) {
        this.upStreamWBSs = upStreamWBSs;
    }

    public String[] getDownStreamWBSs() {
        return downStreamWBSs;
    }

    public void setDownStreamWBSs(String[] downStreamWBSs) {
        this.downStreamWBSs = downStreamWBSs;
    }

}
