package money.project;

import money.project.Model.Methods;
import money.project.view.MainFrameProgram;

/**
 * Created by Alexey Sushko.
 */
public class Main {
    public static void main(String args[]){
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                Methods.Connection();
                Methods.sortedMyListDay();
                Methods.workList();
                new MainFrameProgram();
                Methods.createListForGraphic();
            }
        });




    }
}
