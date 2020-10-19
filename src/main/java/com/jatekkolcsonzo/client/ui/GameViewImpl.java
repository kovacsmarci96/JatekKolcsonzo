package com.jatekkolcsonzo.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.jatekkolcsonzo.client.DTOEntity.GameEntityDTO;
import com.jatekkolcsonzo.client.place.CustomerPlace;
import com.jatekkolcsonzo.client.place.ReservationPlace;
import com.jatekkolcsonzo.client.services.GameServiceAsync;
import com.jatekkolcsonzo.server.services.GameServiceImpl;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class GameViewImpl extends Composite implements GameView{

    interface MyUiBinder extends UiBinder<Widget, GameViewImpl> {}
    private static final GameViewImpl.MyUiBinder uiBinder = GWT.create(GameViewImpl.MyUiBinder.class);

    private Presenter listener;
    private String name;

    @Override
    public void setPresenter(Presenter listener) {
        this.listener = listener;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private GameServiceAsync gameServiceAsync = GameServiceImpl.App.getInstance();
    private List<GameEntityDTO> games = new ArrayList<>();


    @UiField
    MaterialNavBar navBar;

    @UiField
    MaterialDataTable<GameEntityDTO> dataTable;

    @UiField
    MaterialButton fabButton;

    @UiField
    MaterialWindow addWindow;

    @UiField
    MaterialTextBox gameName;

    @UiField
    MaterialTextBox gameType;

    @UiField
    MaterialTextBox gamePublisher;

    @UiField
    MaterialButton addButton;

    @UiField
    MaterialWindow deleteWindow;

    @UiField
    MaterialButton noButton;

    @UiField
    MaterialButton yesButton;

    public GameViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        setupFAB();

        setupaddButton();

        setDataTable();
    }

    @UiHandler("gameRefresh")
    void onClickRefresh(ClickEvent e){
        getAllGames();
    }

    @UiHandler("customerLink")
    void onClickGame(ClickEvent e){
        listener.goTo((new CustomerPlace(name)));
    }

    @UiHandler("reservationLink")
    void onClickReservation(ClickEvent e){
        listener.goTo((new ReservationPlace(name)));
    }

    private void setupFAB(){
        fabButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addWindow.open();
                animate(addWindow, Transition.FADEINDOWNBIG);
            }
        });
    }

    private void setupaddButton(){
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                GameEntityDTO game = new GameEntityDTO();
                game.setName(gameName.getText());
                game.setType(gameType.getText());
                game.setPublisher(gamePublisher.getText());
                if(checkIfTextIsEmpty()) {
                    GWT.log("true");
                    MaterialToast.fireToast("Please fill in all gaps", "rounded");
                } else if (checkValidString()){
                    MaterialToast.fireToast("Type and Publisher can only contain letters ", "rounded");
                } else if(checkIFExists(game)){
                    MaterialToast.fireToast("Game is already exists", "rounded");
                } else {
                    games.add(game);
                    addGame(game);
                    clearTextBoxes();
                    MaterialToast.fireToast("Game has been added", "rounded");
                    animate(addWindow,Transition.FADEOUT);
                    closeWindow(addWindow);
                }
                refreshTable();

            }
        });
    }

    private void animate(MaterialWindow pWindow, Transition pTransition){
        MaterialAnimation animation = new MaterialAnimation();
        animation.setTransition(pTransition);
        animation.setDelay(0);
        animation.setDuration(500);
        animation.setInfinite(false);
        animation.animate(pWindow);
    }

    private void closeWindow(MaterialWindow pWindow){
        Timer timer = new Timer() {
            @Override
            public void run() {
                pWindow.close();
            }
        };
        timer.schedule(500);
    }

    private Boolean checkIFExists(GameEntityDTO pGame){
        for(GameEntityDTO game : games){
            if (pGame.getName().toLowerCase().equals(game.getName().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private Boolean checkIfTextIsEmpty(){
        return gameName.getText().equals("") || gameType.getText().equals("") || gamePublisher.getText().equals("");
    }

    private void clearTextBoxes(){
        gameName.setText("");
        gameType.setText("");
        gamePublisher.setText("");
    }

    private void setDataTable(){
        dataTable.getTableTitle().setText("Games");
        dataTable.getTableIcon().setIconType(IconType.GAMEPAD);

        dataTable.addColumn(new TextColumn<GameEntityDTO>() {
            @Override
            public String getValue(GameEntityDTO gameEntityDTO) {
                return "";
            }
        }, "");

        dataTable.addColumn(new TextColumn<GameEntityDTO>() {
            @Override
            public String getValue(GameEntityDTO gameEntityDTO) {
                return gameEntityDTO.getName();
            }
        }, "Name");

        dataTable.addColumn(new TextColumn<GameEntityDTO>() {
            @Override
            public String getValue(GameEntityDTO gameEntityDTO) {
                return gameEntityDTO.getType();
            }
        }, "Type");

        dataTable.addColumn(new TextColumn<GameEntityDTO>() {
            @Override
            public String getValue(GameEntityDTO gameEntityDTO) {
                return gameEntityDTO.getPublisher();
            }
        }, "Publisher");


        dataTable.addColumn(new WidgetColumn<GameEntityDTO, MaterialCheckBox>() {
            @Override
            public MaterialCheckBox getValue(GameEntityDTO gameEntityDTO) {
                MaterialCheckBox mcb = new MaterialCheckBox();
                mcb.setEnabled(false);

                if(gameEntityDTO.isOccupied()){
                    mcb.setValue(true);
                } else {
                    mcb.setValue(false);
                }
                return mcb;
            }
        }, "Reserved");

        dataTable.addColumn(new WidgetColumn<GameEntityDTO, MaterialButton>() {

            @Override
            public MaterialButton getValue(GameEntityDTO gameEntityDTO) {
                MaterialButton btn = new MaterialButton();
                btn.setSize(ButtonSize.MEDIUM);
                btn.setWaves(WavesType.DEFAULT);
                btn.setBackgroundColor(Color.BLUE);
                btn.setType(ButtonType.FLOATING);
                if(gameEntityDTO.isOccupied()){
                    btn.setEnabled(false);
                } else {
                    btn.setEnabled(true);
                }
                btn.setIconType(IconType.DELETE);
                btn.setText("Delete");
                btn.setTooltip("Delete " + gameEntityDTO.getName());
                btn.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {

                        deleteWindow.open();
                        animate(deleteWindow,Transition.FADEINDOWNBIG);

                        yesButton.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                animate(deleteWindow,Transition.FADEOUT);
                                closeWindow(deleteWindow);

                                deleteGame(gameEntityDTO.getName());
                                games.remove(gameEntityDTO);
                                refreshTable();
                            }
                        });

                        noButton.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                animate(deleteWindow,Transition.FADEOUT);
                                closeWindow(deleteWindow);
                            }
                        });
                    }
                });

                return btn;
            }
        }, "");

    }

    private void addGame(GameEntityDTO pGame){
        AsyncCallback<Void> gameDTOAsyncCallback = new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Void result) {

            }
        };
        gameServiceAsync.addGame(pGame,gameDTOAsyncCallback);
    }

    public void getAllGames(){

        AsyncCallback<List<GameEntityDTO>> gameDTOAsyncCallback = new AsyncCallback<List<GameEntityDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                GWT.log(caught.getMessage());
            }

            @Override
            public void onSuccess(List<GameEntityDTO> result) {
                games = result;
                dataTable.setVisibleRange(0, games.size());
                dataTable.setRowData(0, games);
            }
        };

        gameServiceAsync.getAllGame(gameDTOAsyncCallback);
    }

    private void deleteGame(String name){
        AsyncCallback<Void> gameDTOAsyncCallback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Void result) {
            }
        };
        gameServiceAsync.deleteGame(name,gameDTOAsyncCallback);
    }

    private void refreshTable(){
        dataTable.setVisibleRange(0,games.size());
        dataTable.setRowData(0,games);
        dataTable.getView().setRedraw(true);
        dataTable.getView().refresh();
    }

    private boolean checkValidString(){
        String valid = "^[a-zA-Z]+$";
        return  (!gameType.getText().matches(valid) || !gamePublisher.getText().matches(valid));
    }
}
