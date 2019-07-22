package statemachine;

import java.util.HashMap;
import java.util.Map;
import java.lang.*;
import java.util.Objects;

/**
 * Simple tx sequence diagram  https://goo.gl/QECT8r
 */
public class StateMachineApp {
    public static void main(String[] args) {
        StateMachine sm = new StateMachine();
        sm.add(new Transition(TxEvent.OPEN, TxState.INITIAL, TxState.OPENED), new OpenTx());
        sm.add(new Transition(TxEvent.COMMIT, TxState.OPENED, TxState.COMMITTED), new CommitTx());
        sm.add(new Transition(TxEvent.ROLLBACK, TxState.OPENED, TxState.FAILED), new RollbackTx());

        System.out.println("\nFirst transaction\n");
        sm.update(TxEvent.OPEN);
        sm.update(TxEvent.COMMIT);

        sm.reset();

        System.out.println("\nSecond transaction\n");
        sm.update(TxEvent.OPEN);
        sm.update(TxEvent.ROLLBACK);
    }
}

/**
 * transition graph:
 * {event     source     target}
 * {open,     initial -> opened}
 * {open,     opened  ->  commited} //fail
 * {commit,   opened  ->  commited} //ok
 * {commit,   initial -> commited}  //fail
 * {rollback, opened  ->  failed}   //ok
 * {rollback, failed  ->  open}     //fail
 */
class StateMachine {
    //indexing transitions by event type
    //Map<TxEvent, Set<Transition>> transitionGraph = new HashMap<>();
    final Map<Transition, TxAction> actionsGraph = new HashMap<>();
    TxState currentState = TxState.INITIAL;

    /**
     * Link a transition with the action to execute the action
     * @param transition transition
     * @param action contains business logic
     */
    void add(Transition transition, TxAction action) {
        /*if (!transitionGraph.containsKey(transition.event)) {
            transitionGraph.put(transition.event, new HashSet<>());
        }

        Set<Transition> transitions = transitionGraph.get(transition.event);
        if (transitions.contains(transition)) {
            throw new RuntimeException("Transition already defined: " + transition);
        }*/

        //transitions.add(transition);
        actionsGraph.put(transition, action);
        
        //sm.reset();

        System.out.println("\n Invalid transaction\n");
        //sm.update(TxEvent.COMMIT);
    }

    void update(TxEvent event) {
        Map<TxEvent, TxState> trigger = new HashMap<>();
        trigger.put(TxEvent.OPEN, TxState.OPENED);
        trigger.put(TxEvent.COMMIT, TxState.COMMITTED);
        trigger.put(TxEvent.ROLLBACK, TxState.FAILED);

        Transition transition = new Transition(event, currentState, trigger.get(event));

        if(!actionsGraph.containsKey(transition)){
            System.out.println("Illegal action: " + transition);
            return;
        }
        actionsGraph.get(transition).execute();
        currentState = transition.target;
    }

    void reset(){
        currentState = TxState.INITIAL;
    }
}

/**
 * Transition from source state to target state.
 * An event is a trigger which changes the state
 */
class Transition {
    final TxEvent event;
    final TxState source;
    final TxState target;

    Transition(TxEvent event, TxState source, TxState target) {
        this.event = event;
        this.source = source;
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return event == that.event &&
                source == that.source &&
                target == that.target;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, source, target);
    }

    @Override
    public String toString() {
        return "Transition{" + "event=" + event + ", source=" + source + ", target=" + target + '}';
    }
}

enum TxState {
    INITIAL, OPENED, COMMITTED, FAILED
}

enum TxEvent {
    OPEN, COMMIT, ROLLBACK
}


//Actions
interface TxAction {
    void execute();
}

class OpenTx implements TxAction {
    public void execute() {
        System.out.println("Open transaction action");
    }
}

class CommitTx implements TxAction {
    public void execute() {
        System.out.println("Commit transaction action");
    }
}

class RollbackTx implements TxAction {
    public void execute() {
        System.out.println("Rollback transaction action");
    }
}
