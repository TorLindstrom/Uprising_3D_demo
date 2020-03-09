package tor.controller;

public class App
{
    //TODO: add settings, for example: allow "folding" sides (true, false), and these are then used and check to optimize the code when out of use
    //screen size
    //pixels on that screen (could be wider pixels than one "actual" pixel)
    //ambient occlusion settings, number of bounces, and number of rays
    //view distance
    //anti aliasing check rays
    //sensitivity
    //FOV
    //and so on...
    //maybe store as a map, with key/value pairs (ofc if map)
    public static void main( String[] args ) throws InterruptedException
    {
        //and send the settings to the manager
        //and let manager handle the settings, and update the appropriate modules accordingly if on the fly
        new Manager(args);
    }
}
