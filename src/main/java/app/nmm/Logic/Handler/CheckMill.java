package app.nmm.Logic.Handler;

import app.nmm.Logic.Location.Node;
import javafx.scene.Parent;
import org.javatuples.Pair;
//import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CheckMill {
    // 0:H 1:V
    private final Map<Integer, ArrayList<ArrayList<Integer>>> millPosition = new HashMap<Integer, ArrayList<ArrayList<Integer>>>() {{
        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(9, 21)))));

        put(1, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(0, 2)),
                new ArrayList<Integer>(Arrays.asList(4, 7)))));

        put(2, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(0, 1)),
                new ArrayList<Integer>(Arrays.asList(14, 23)))));

        put(3, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(4, 5)),
                new ArrayList<Integer>(Arrays.asList(10, 18)))));

        put(4, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(3,5)),
                new ArrayList<Integer>(Arrays.asList(1,7)))));

        put(5, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(3, 4)),
                new ArrayList<Integer>(Arrays.asList(13, 20)))));

        put(6, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(7, 8)),
                new ArrayList<Integer>(Arrays.asList(11, 15)))));

        put(7, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(6, 8)),
                new ArrayList<Integer>(Arrays.asList(1, 4)))));

        put(8, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(6, 7)),
                new ArrayList<Integer>(Arrays.asList(12, 17)))));

        put(9, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(10, 11)),
                new ArrayList<Integer>(Arrays.asList(0, 21)))));

        put(10, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(9, 11)),
                new ArrayList<Integer>(Arrays.asList(3, 18)))));

        put(11, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(9, 10)),
                new ArrayList<Integer>(Arrays.asList(6, 15)))));

        put(12, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(13, 14)),
                new ArrayList<Integer>(Arrays.asList(8, 17)))));

        put(13, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(12, 14)),
                new ArrayList<Integer>(Arrays.asList(5, 20)))));

        put(14, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(12, 13)),
                new ArrayList<Integer>(Arrays.asList(2, 23)))));

        put(15, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(16, 17)),
                new ArrayList<Integer>(Arrays.asList(6, 11)))));

        put(16, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(15, 17)),
                new ArrayList<Integer>(Arrays.asList(19, 22)))));

        put(17, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(15, 16)),
                new ArrayList<Integer>(Arrays.asList(8, 12)))));

        put(18, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(19,20)),
                new ArrayList<Integer>(Arrays.asList(3,10)))));

        put(19, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(18, 20)),
                new ArrayList<Integer>(Arrays.asList(16, 22)))));

        put(20, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(18, 19)),
                new ArrayList<Integer>(Arrays.asList(5, 13)))));

        put(21, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(22,23)),
                new ArrayList<Integer>(Arrays.asList(0,9)))));

        put(22, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(21, 23)),
                new ArrayList<Integer>(Arrays.asList(16, 19)))));

        put(23, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(21, 22)),
                new ArrayList<Integer>(Arrays.asList(2, 14)))));

    }};

    private ArrayList<ArrayList<Integer>> millNodes;

    public CheckMill(){
        this.millNodes= new ArrayList<ArrayList<Integer>>();
    }


    public ArrayList<ArrayList<Integer>> getMillNodes(Integer id){
        return this.millPosition.get(id);
    }

    /**
     * check whether a mill is form after token is placed on the node
     * @param nodeList list of all nodes on the board
     * @param targetPosition the node where player recently placed token on
     * @return true if a mill is formed
     */
    public ArrayList<Boolean> checkPossibleMill(ArrayList<Node> nodeList, Integer targetPosition) {

        // get corresponding mill list
        ArrayList<ArrayList<Integer>> nodeToCheck = millPosition.get(targetPosition);
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        result.add(false);
        result.add(false);
        this.millNodes = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < nodeToCheck.size(); i++) {

            Integer millCheck = 0; // if it reaches 2 means mill is formed

            for (int j = 0; j < nodeToCheck.get(i).size(); j++) {

                Integer theNode = nodeToCheck.get(i).get(j);

                if (nodeList.get(theNode).getToken() != null) {


                    // if the node contains player's token
                    if (nodeList.get(theNode).getToken().getColour() == nodeList.get(targetPosition).getToken().getColour()) {
                        millCheck += 1;
                    }
                    else{
                        break;
                    }

                }

                if (millCheck == 2 && i==0){ // first mill found
                    result.set(0,true);
                    this.millNodes.add(nodeToCheck.get(i));
                }
                else if (millCheck == 2 && i==1){ // second mill found
                    result.set(1,true);
                    this.millNodes.add(nodeToCheck.get(i));
                }
            }
        }
        return result;
    }


    /**
     * check whether a mill is form after token is placed on the node
     * @param nodeList list of all nodes on the board
     * @param targetPosition the node where player recently placed token on
     * @param  colour colour of current Player
     * @return true if a mill is formed
     */
    public Pair<ArrayList<Boolean>,ArrayList<ArrayList<Integer>>> checkPossibleMill(ArrayList<Node> nodeList, Integer targetPosition, String colour, String status) {

        // get corresponding mill list
        ArrayList<ArrayList<Integer>> nodeToCheck = millPosition.get(targetPosition);
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        result.add(false);
        result.add(false);

        this.millNodes = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < nodeToCheck.size(); i++) {

            Integer millCheck = 0; // if it reaches 2 means mill is formed

            for (int j = 0; j < nodeToCheck.get(i).size(); j++) {

                Integer theNode = nodeToCheck.get(i).get(j);

                if (nodeList.get(theNode).getToken() != null) {

                    // for token to find action
                    if (colour != null && nodeList.get(theNode).getToken().getColour() == colour){
                        millCheck += 1;
                    }
                    // if the node contains player's token
                    else if (colour == null && nodeList.get(theNode).getToken().getColour() == nodeList.get(targetPosition).getToken().getColour()) {
                        millCheck += 1;
                    }
                    else{
                        break;
                    }

                }

                if (millCheck == 2 && i==0){ // first mill found
                    result.set(0,true);
                    this.millNodes.add(nodeToCheck.get(i));

                }
                else if (millCheck == 2 && i==1){ // second mill found
                    result.set(1,true);
                    this.millNodes.add(nodeToCheck.get(i));
                }
                else if (status == "CheckOpponent" && millCheck == 1 && i==0){ // second mill found
                    result.set(0,true);
                    this.millNodes.add(nodeToCheck.get(i));
                }
                else if (status == "CheckOpponent"  && millCheck == 1 && i==1){ // second mill found
                    result.set(1,true);
                    this.millNodes.add(nodeToCheck.get(i));
                }

            }
        }
        return new Pair<>(result,nodeToCheck);
    }


}