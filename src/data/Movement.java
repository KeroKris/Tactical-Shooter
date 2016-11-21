package data;

/**
 * Created by Kristoffer on 2016-09-06.
 */
public interface Movement {
    int moving();
}


class RunningMode implements Movement{

    public int moving(){

        return 85;
    }

}

class WalkingMode implements Movement{

    public int moving(){
        return 50;
    }
}