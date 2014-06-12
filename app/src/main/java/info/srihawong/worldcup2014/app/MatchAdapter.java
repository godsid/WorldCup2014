package info.srihawong.worldcup2014.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import info.srihawong.worldcup2014.app.Object.Match;

/**
 * Created by Banpot.S on 6/12/14 AD.
 */
public class MatchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Match> matchArrayList;
    private LayoutInflater mInflater;

    public MatchAdapter(Context context, ArrayList<Match> matchArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.matchArrayList = matchArrayList;
    }

    @Override
    public int getCount() {
        return matchArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return matchArrayList.get(position).getTimestamp();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        MatchItemView matchItemView;
        Match match = matchArrayList.get(position);
        if(view==null){
            view = mInflater.inflate(R.layout.list_match,null);
            matchItemView = new MatchItemView();
            matchItemView.groupTextView =  (TextView) view.findViewById(R.id.groupTextView);
            matchItemView.timeTextView = (TextView) view.findViewById(R.id.timeTextView);
            matchItemView.team1ImageView = (ImageView) view.findViewById(R.id.team1ImageView);
            matchItemView.team2ImageView = (ImageView) view.findViewById(R.id.team2ImageView);
            matchItemView.channelImageView = (ImageView) view.findViewById(R.id.channelImageView);
            view.setTag(matchItemView);
        }else{
            matchItemView = (MatchItemView) view.getTag();
        }


        matchItemView.groupTextView.setText(match.getGroup());
        matchItemView.timeTextView.setText(ThaiDate.format(match.getTimestamp(),Config.timeFormat));

        //matchItemView.team1ImageView.setImageResource(R.drawable.flag_default);
        //matchItemView.team2ImageView.setImageResource(R.drawable.flag_default);
        //Log.d("tui",Config.apiURL+"flags/"+match.getTeam1().toLowerCase()+".png");

        AQuery aq = new AQuery(view.getContext())
                .id(matchItemView.team1ImageView)
                .image(Config.apiURL+"flags/"+match.getTeam1().toLowerCase()+".png",true,true,0,0,null,AQuery.FADE_IN_NETWORK);
        aq.id(matchItemView.team2ImageView)
                .image(Config.apiURL+"flags/"+match.getTeam2().toLowerCase()+".png",true,true,0,0,null,AQuery.FADE_IN_NETWORK);

        //placeItemView.distance.setText(Util.getDistance());
        /*
        AQuery aq = new AQuery(view.getContext())
                .id(placeItemView.user_avatar)
                .image(place.getMember().getAvatar(),true,true,0,0,null,AQuery.FADE_IN_NETWORK,1.0f / 1.0f);
        */
        return view;
    }


}

class MatchItemView{
    TextView groupTextView,timeTextView;
    ImageView channelImageView,team1ImageView,team2ImageView;
}
