package edu.wpi.cs.wpisuitetng.modules.core.models;

public enum Carrier {
    THREE_RIVER_WIRELESS("sms.3rivers.net", "3 River Wireless"),
    ACS_WIRELESS("paging.acswireless.com", "ACS Wireless"),
    ALLTEL("message.alltel.com", "Alltel"),
    ATT("txt.att.net", "AT&T"),
    BLUE_SKY_FROG("blueskyfrog.com", "Blue Sky Frog"),
    BLUEGRASS_CELLULAR("sms.bluecell.com", "Bluegrass Cellular"),
    BOOST_MOBILE("myboostmobile.com", "Boost Mobile"),
    BPL_MOBILE("bplmobile.com", "BPL Mobile"),
    CAROLINA_WEST_WIRELESS("cwwsms.com", "Carolina West Wireless"),
    CELLULAR_ONE("mobile.celloneusa.com", "Cellular One"),
    CELLULAR_SOUTH("csouth1.com", "Cellular South"),
    CENTENNIAL_WIRELESS("cwemail.com", "Centennial Wireless"),
    CENTURYTEL("messaging.centurytel.net", "CenturyTel"),
    CLEARNET("msg.clearnet.com", "Clearnet"),
    COMCAST("comcastpcs.textmsg.com", "Comcast"),
    CORR_WIRELESS_COMMUNICATIONS("corrwireless.net", "Corr Wireless Communications"),
    DOBSON("mobile.dobson.net", "Dobson"),
    EDGE_WIRELESS("sms.edgewireless.com", "Edge Wireless"),
    FIDO("fido.ca", "Fido"),
    GOLDEN_TELECOM("sms.goldentele.com", "Golden Telecom"),
    HELIO("messaging.sprintpcs.com", "Helio"),
    HOUSTON_CELLULAR("text.houstoncellular.net", "Houston Cellular"),
    IDEA_CELLULAR("ideacellular.net", "Idea Cellular"),
    ILLINOIS_VALLEY_CELLULAR("ivctext.com", "Illinois Valley Cellular"),
    INLAND_CELLULAR_TELEPHONE("inlandlink.com", "Inland Cellular Telephone"),
    MCI("pagemci.com", "MCI"),
    METROCALL("page.metrocall.com", "Metrocall"),
    METROCALL2WAY("my2way.com", "Metrocall 2-way"),
    METRO_PCS("mymetropcs.com", "Metro PCS"),
    MICROCELL("fido.ca", "Microcell"),
    MIDWEST_WIRELESS("clearlydigital.com", "Midwest Wireless"),
    MOBILCOMM("mobilecomm.net", "Mobilcomm"),
    MTS("text.mtsmobility.com", "MTS"),
    NEXTEL("messaging.nextel.com", "Nextel"),
    ONLINEBEEP("onlinebeep.net", "Online Beep"),
    PCS_ONE("pcsone.net", "PCS One"),
    PRESIDENTS_CHOICE("txt.bell.ca", "President's Choice"),
    PUBLIC_SERVICE_CELLULAR("sms.pscel.com", "Public Service Cellular"),
    QWEST("qwestmp.com", "Qwest"),
    SATELLINK("satellink.net", "Satellink"),
    SOLO_MOBILE("txt.bell.ca", "Solo Mobile"),
    SOUTHWESTERN_BELL("email.swbw.com", "Southwestern Bell"),
    SPRINT("messaging.sprintpcs.com", "Sprint"),
    SUMCOM("tms.suncom.com", "Sumcom"),
    SUREWEST_COMMUNICATIONS("mobile.surewest.com", "Surewest Communications"),
    T_MOBILE("tmomail.net", "T-Mobile"),
    TELUS("msg.telus.com", "Telus"),
    TRACFONE("txt.att.net", "Tracfone"),
    TRITON("tms.suncom.com", "Triton"),
    UNICEL("utext.com", "Unicel"),
    UNKNOWN("", "Unknown"),
    US_CELLULAR("email.uscc.net", "US Cellular"),
    US_WEST("uswestdatamail.com", "US West"),
    VERIZON("vtext.com", "Verizon"),
    VIRGIN_MOBILE("vmobl.com", "Virgin Mobile"),
    VIRGIN_MOBILE_CA("vmobile.ca", "Virgin Mobile Canada"),
    WEST_CENTRAL_WIRELESS("sms.wcc.net", "West Central Wireless"),
    WESTERN_WIRELESS("cellularonewest.com", "Western Wireless");

    private final String url;
    private String labelText;
    
    private Carrier(String url, String labelText) {
        this.url = url;
        this.labelText = labelText;
    }
    
    public String getURL() {
        return url;
    }
    
    public String getLabelText() {
        return labelText;
    }
    
    @Override
    public String toString() {
        return labelText;
    }
    
    public static Carrier getEnum(String value) {
        for (Carrier v: values()) {
            if(v.labelText.compareTo(value) == 0) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }
}
