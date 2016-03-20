package xyz.shaneneary.laundryview.objects;

/**
 * Created by TheTrueSaiyan on 3/14/2016.
 */
public class DormMachines {
    public String URL;
    public String dormName;
    public int washersFree;
    public int washersInUse;
    public int dryersFree;
    public int dryersInUse;
//    public String washersFree;
//    public String washersInUse;
//    public String dryersFree;
//    public String dryersInUse;

    public DormMachines() {
    }

    public DormMachines(String URL, String dormName, int washersFree, int washersInUse, int dryersFree, int dryersInUse) {
        this.URL = URL;
        this.dormName = dormName;
        this.washersFree = washersFree;
        this.washersInUse = washersInUse;
        this.dryersFree = dryersFree;
        this.dryersInUse = dryersInUse;
    }
}
