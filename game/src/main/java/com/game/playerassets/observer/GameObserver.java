package com.game.playerassets.observer;


import com.game.playerassets.ia.Player;
import com.utils.Event;

public class GameObserver {
    private final Player player;
    private final Container container;

    public GameObserver(Player p) {
        container = new Container();
        this.player = p;
    }

    /**
     * @param go    le GameObserver du joueur faisant l'actions
     * @param event l'action effectué apr le joueur
     * @param o     l'objet en rapport avec l'action, ex : carte invoquée, nombre d'energies crystalisées,... l'objet peut etre null dans une action où il est inutile
     */
    public void update(GameObserver go, Event event, Object o) {

        Player playerAction = go.getPlayer(); //repésente le player ayant fait l'action

        PlayerContainer playerContainerAction = container.getDictPlayerContainer().get(playerAction); //référence au conteneur de data pour ce player dans l'observer courant
        switch (event) {
            case INVOKE -> {
                playerContainerAction.updateInvokedCards(playerAction);
            }
            case CRYSTALLIZE -> {
                playerContainerAction.updateCrystalPoint(playerAction);
                //update energy
                playerContainerAction.updateEnergies(playerAction.getFacadeIA().getAmountOfEnergiesArrayF());
            }
            case USE_BONUS -> playerContainerAction.updateBonusUsed();
            case DRAW_CARD, DRAW_CARD_WITH_BONUS -> playerContainerAction.updateNbCards((int) o);
            case INCREASE_INVOKE_CAPACITY -> playerContainerAction.updateInvokeGauge((int) o);
            case SACRIFICE -> playerContainerAction.updateInvokedCards(playerAction);
            case GAIN_CRYSTAL -> playerContainerAction.updateCrystalPoint(playerAction);
            case GAIN_ENERGY -> playerContainerAction.updateEnergies(playerAction.getFacadeIA().getAmountOfEnergiesArrayF());
            default -> {}
        }
    }

    public void registerPlayer(Player p) {
        container.registerPlayerContainer(p);
    }

    public Container getContainer() {
        return container;
    }

    public Player getPlayer() {
        return player;
    }

    public void reset(){
        container.reset();
    }
}