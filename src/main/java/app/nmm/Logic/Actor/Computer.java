package app.nmm.Logic.Actor;

import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.MoveTokenAction;
import app.nmm.Logic.Handler.CheckMill;
import app.nmm.Logic.Location.Node;
import app.nmm.Logic.Token.Token;
import org.javatuples.Pair;

import java.util.*;

public class Computer extends Actor {
    Integer initialPos =  null;
    Integer finalPos =  null;
    public Computer(String tokenColour, String playerName, int i) {
        super(tokenColour, playerName, i);
    }


    public Action getAction(Map<Integer,ArrayList<Action>> allowableActionList, ArrayList<Action> opponentAllowableActionList, ArrayList<Node> nodeList, CheckMill millChecker){
        Iterator<Integer> tokenPositionList =  allowableActionList.keySet().iterator();
        Map<Integer,Action> actionMovementMap =  new HashMap<>();
        Action output =  null;
        while(tokenPositionList.hasNext()){
            int tokenID =  tokenPositionList.next();

            for(Action action : allowableActionList.get(tokenID)){
                System.out.println(action.toString());
                int targetID =  ((MoveTokenAction) action).getTargetId();
                actionMovementMap.put(targetID,action);

                Pair<ArrayList<Boolean>,ArrayList<ArrayList<Integer>>> result = millChecker.checkPossibleMill(nodeList, targetID, "Black", null);

                if ((result.getValue0().get(0) == true || result.getValue0().get(1) == true)) {
                    if (initialPos == null && finalPos == null) {
                        initialPos =  tokenID;
                        finalPos =  targetID;
                        output = action;
                        System.out.println("action found in trying to form a mill");
                        System.out.println(output.toString());
                    }
                    else if (initialPos != targetID && finalPos != tokenID){
                        initialPos =  tokenID;
                        finalPos =  targetID;
                        output = action;
                        System.out.println("action found in trying to form a mill");
                        System.out.println(output.toString());
                    }


                    if (output != null){
                        return  output;

                    }
                }
            }

        }



        for (Action action : opponentAllowableActionList) {

            int targetID = action.getNodeId();

            Pair<ArrayList<Boolean>,ArrayList<ArrayList<Integer>>> result = millChecker.checkPossibleMill(nodeList, targetID, null, "CheckOpponent");

            if ((result.getValue0().get(0) == true || result.getValue0().get(1) == true)) {

                if (result.getValue0().get(0) == true){
                    for (int i : result.getValue1().get(0)){
                        if (actionMovementMap.containsKey(i)){
                            if ((initialPos != null && finalPos != null) && (initialPos != targetID && finalPos != i) ){
                                initialPos =  i;
                                finalPos =  targetID;
                                output =  actionMovementMap.get(i);
                                System.out.println("action found in trying to block opponent");
                                System.out.println(output.toString());
                                return output;
                            }
                            else if (initialPos == null && finalPos == null){
                                initialPos =  i;
                                finalPos =  targetID;
                                output =  actionMovementMap.get(i);
                                System.out.println("action found in trying to block opponent");
                                System.out.println(output.toString());
                                return output;
                            }

                        }
                    }
                }
                else{
                    for (int i : result.getValue1().get(1)){
                        if (actionMovementMap.containsKey(i)){
                            if ((initialPos != null && finalPos != null) && (initialPos != targetID && finalPos != i) ){
                                initialPos =  i;
                                finalPos =  targetID;
                                output =  actionMovementMap.get(i);
                                System.out.println("action found in trying to block opponent");
                                System.out.println(output.toString());
                                return output;
                            }
                            else if (initialPos == null && finalPos == null){
                                initialPos =  i;
                                finalPos =  targetID;
                                output =  actionMovementMap.get(i);
                                System.out.println("action found in trying to block opponent");
                                System.out.println(output.toString());
                                return output;
                            }

                        }
                    }
                }
                break;
            }
        }

        while (output == null){
            Set<Integer> keySet =  allowableActionList.keySet();
            List<Integer> keyList = new ArrayList<>(keySet);
            int size = 0;
            int randomKey = -1;
            int randIdx =  -1;
            while (size == 0){
                size = keyList.size();
                 randIdx = new Random().nextInt(size);

                randomKey = keyList.get(randIdx);
                size = allowableActionList.get(randomKey).size();
            }
             randIdx =  new Random().nextInt(size);

            output = allowableActionList.get(randomKey).get(randIdx);

            boolean cond1 = (finalPos != null && initialPos != null);
            if (!cond1){
                initialPos = output.getNodeId();
                finalPos = ((MoveTokenAction) output).getTargetId();
                System.out.println(output.toString());
                return output;
            }
            boolean cond2 = (output.getNodeId() == finalPos && ((MoveTokenAction) output).getTargetId() == initialPos);
            if (cond1&& ! cond2){
                initialPos = output.getNodeId();
                finalPos = ((MoveTokenAction) output).getTargetId();
                System.out.println(output.toString());
            }
            else{
                output = null;
            }


        }


        return  output;
    }




    public Action getAction(ArrayList<Action> allowableActionList, ArrayList<Action> opponentAllowableActionList, ArrayList<Node> nodeList, CheckMill millChecker){
        Action output =  null;
        Map<Integer,Action> actionMovementMap =  new HashMap<>();
        for (Action action : allowableActionList){

            int tokenID = 24;

            int targetID =   action.getNodeId();

            Pair<ArrayList<Boolean>,ArrayList<ArrayList<Integer>>> result  = millChecker.checkPossibleMill(nodeList, targetID, "Black",null);
            actionMovementMap.put(targetID,action);

            if ((result.getValue0().get(0) == true || result.getValue0().get(1) == true)) {
                    output = action;
                    break;
            }
        }



        if (output == null){
            for (Action action : opponentAllowableActionList) {

                int targetID = action.getNodeId();

                Pair<ArrayList<Boolean>,ArrayList<ArrayList<Integer>>> result = millChecker.checkPossibleMill(nodeList, targetID, null, "CheckOpponent");

                if ((result.getValue0().get(0) == true || result.getValue0().get(1) == true)) {

                    if (result.getValue0().get(0) == true){
                        for (int i : result.getValue1().get(0)){
                            if (actionMovementMap.containsKey(i)){
                                output = actionMovementMap.get(i);
                                break;
                            }
                        }
                    }
                    else{
                        for (int i : result.getValue1().get(1)){
                            if (actionMovementMap.containsKey(i)){
                                output = actionMovementMap.get(i);
                                break;
                            }
                        }
                    }
                    if (output != null){
                        break;
                    }
                }
            }
        }



        if (output == null){

            int size = allowableActionList.size();
            int randIdx = new Random().nextInt(size);

            output = allowableActionList.get(randIdx);
        }



        return output;

    }

    /**
     * This method calibrates the token to be removed from all the possible tokens that can be removed on board
     * This heuristic prioritizes if any row is occupied by 2 of the opponent tokens
     * @param removeOpponentTokenActionList Possible tokens that can be removed
     * @param nodeList The list of nodes
     * @param millChecker To check which group of nodes can potentially form a mill
     * @return
     */
    public Action getRemoveAction(ArrayList<Action> removeOpponentTokenActionList, ArrayList<Node> nodeList, CheckMill millChecker){
        Action output =  null; // initializes the output to be null

        // Loops through the list of tokens that can be removed to calculate which token to remove
        for (Action action : removeOpponentTokenActionList){
            int id = action.getNodeId();
            // to get the neighbouring nodes that can form a mill based on the node where the removable token is at
            ArrayList<ArrayList<Integer>> nodeToCheck = millChecker.getMillNodes(id);

            // if 2/3 nodes in a row is occupied by an opponent's token, select that token to be removed
            for (int i=0; i<nodeToCheck.size();i++){
                for (int j=0; j<nodeToCheck.get(i).size();j++){
                    Integer millFormingNodes = nodeToCheck.get(i).get(j);
                    if(nodeList.get(millFormingNodes).hasToken() && nodeList.get(millFormingNodes).getToken().getColour() == nodeList.get(id).getToken().getColour()){
                            output = action;
                            break;
                    }
                }
            }
        }
        // if no tokens were found based on the above heuristic, randomly select one removable token on board
        if (output == null){

            int size = removeOpponentTokenActionList.size();
            int randIdx = new Random().nextInt(size);

            output = removeOpponentTokenActionList.get(randIdx);
        }

        return output;

    }


}
