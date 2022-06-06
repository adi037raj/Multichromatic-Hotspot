package multichromatic;

import java.util.ArrayList;

public class ChangeDetection{

    int x,y,start,end;
    int radius;
    int height;
    double llr,entropy;
    ArrayList<Integer> colorlist;
    int daystart,dayend;
    ChangeDetection(int x,int y,int start,int end,int radius,int height,double llr, ArrayList<Integer>  colorlist,double entropy,int daystart,int dayend)
    {
        this.x=x;
        this.y=y;
        this.start=start;
        this.llr=llr;
        this.entropy=entropy;
        this.colorlist=colorlist;
        this.end=end;
        this.radius=radius;
        this.height=height;
        this.dayend=dayend;
        this.daystart=daystart;

    }
    ChangeDetection(ChangeDetection c)
    {
        this.x=c.x;
        this.y=c.y;
        this.start=c.start;
        this.llr=c.llr;
        this.colorlist=c.colorlist;
        this.end=c.end;
        this.dayend=c.dayend;
        this.daystart=c.daystart;

        this.radius=c.radius;
        this.entropy=c.entropy;
        this.height=c.height;
    }



}