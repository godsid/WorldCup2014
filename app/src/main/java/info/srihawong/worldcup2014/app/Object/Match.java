package info.srihawong.worldcup2014.app.Object;

/**
 * Created by Banpot.S on 6/12/14 AD.
 */
public class Match {
    long timestamp;
    String group,team1,team2,scroll;
    String[] channel;
    boolean alarm = false;

    public Match(long timestamp, String group, String team1, String team2) {
        this.timestamp = timestamp;
        this.group = group;
        this.team1 = team1;
        this.team2 = team2;

    }
    public Match(long timestamp, String group, String team1, String team2,String channel[],Boolean alarm) {
        this.timestamp = timestamp;
        this.group = group;
        this.team1 = team1;
        this.team2 = team2;
        this.channel = channel;
        this.alarm = alarm;
    }

    public String[] getChannel() {
        return channel;
    }

    public void setChannel(String[] channel) {
        this.channel = channel;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScroll() {
        return scroll;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }
}
