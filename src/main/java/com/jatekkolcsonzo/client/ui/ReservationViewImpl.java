package com.jatekkolcsonzo.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.jatekkolcsonzo.client.DTOEntity.CustomerEntityDTO;
import com.jatekkolcsonzo.client.DTOEntity.GameEntityDTO;
import com.jatekkolcsonzo.client.DTOEntity.ReservationEntityDTO;
import com.jatekkolcsonzo.client.place.CustomerPlace;
import com.jatekkolcsonzo.client.place.GamePlace;
import com.jatekkolcsonzo.client.services.CustomerServiceAsync;
import com.jatekkolcsonzo.client.services.GameServiceAsync;
import com.jatekkolcsonzo.client.services.ReservationServiceAsync;
import com.jatekkolcsonzo.server.services.CustomerServiceImpl;
import com.jatekkolcsonzo.server.services.GameServiceImpl;
import com.jatekkolcsonzo.server.services.ReservationServiceImpl;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class ReservationViewImpl extends Composite implements ReservationView {

    interface MyUiBinder extends UiBinder<Widget, ReservationViewImpl> {}
    private static final ReservationViewImpl.MyUiBinder uiBinder = GWT.create(ReservationViewImpl.MyUiBinder.class);

    private Presenter listener;
    private String name;

    @Override
    public void setName(String reservationName) {
        this.name = reservationName;
    }
    @Override
    public void setPresenter(Presenter listener) {
        this.listener = listener;
    }

    private CustomerServiceAsync customerServiceAsync = CustomerServiceImpl.App.getInstance();
    private GameServiceAsync gameServiceAsync = GameServiceImpl.App.getInstance();
    private ReservationServiceAsync reservationServiceAsync = ReservationServiceImpl.App.getInstance();


    private List<CustomerEntityDTO> customers = new ArrayList<>();
    private List<GameEntityDTO> games = new ArrayList<>();
    private List<ReservationEntityDTO> reservations = new ArrayList<>();


    @UiField
    MaterialNavBar navBar;

    @UiField
    MaterialDataTable<ReservationEntityDTO> dataTable;

    @UiField
    MaterialButton fabButton;

    @UiField
    MaterialWindow addWindow;

    @UiField
    MaterialButton addButton;

    @UiField
    MaterialWindow deleteWindow;

    @UiField
    MaterialButton noButton;

    @UiField
    MaterialButton yesButton;

    @UiField
    MaterialDatePicker startDatePicker;

    @UiField
    MaterialDatePicker endDatePicker;

    @UiField
    MaterialSideNavDrawer sideNav;

    @UiField
    MaterialComboBox<CustomerEntityDTO> customerComboBox;

    @UiField
    MaterialComboBox<GameEntityDTO> gameComboBox;

    public ReservationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        addWindow.addCloseHandler(new CloseHandler<Boolean>() {
            @Override
            public void onClose(CloseEvent<Boolean> event) {
                clearAddWindow();
            }
        });

        getAllCustomer();
        getAllGame();

        setupFAB();

        setupaddButton();

        setupDatePickers();

        getAllReservation();

        setDataTable();

    }

    @UiHandler("customerLink")
    void onClickGame(ClickEvent e){
        listener.goTo((new CustomerPlace(name)));
    }

    @UiHandler("gameLink")
    void onClickReservation(ClickEvent e){
        listener.goTo((new GamePlace(name)));
    }

    @UiHandler("resRefresh")
    void onClickRefresh(ClickEvent e){
        getAllReservation();
    }

    private String setupDate(Date pDate){
        JsDate date = JsDate.create(pDate.getTime());
        int year = date.getFullYear();
        int month = date.getMonth() + 1;
        int day = date.getDate();
        String sDay = String.valueOf(day);
        if(day < 10){
            sDay = "0" + sDay;
        }
        return year + "." + month + "." + sDay + ".";
    }

    private boolean checkifTimeisEmpty(MaterialDatePicker pPicker){
        if(pPicker.getValue() == null){
            return true;
        }
        return false;
    }

    private void setupDatePickers(){
        Date start = new Date();
        startDatePicker.setDateMin(start);
        long nowTime = start.getTime();
        Date startMax = new Date();
        startMax.setTime(nowTime + 604800000L);
        startDatePicker.setDateMax(startMax);

        Date end = new Date();
        end.setTime(nowTime + 86400000L);
        endDatePicker.setDateMin(end);

        Date endMax = new Date();
        endMax.setTime(nowTime + 1209600000L);
        endDatePicker.setDateMax(endMax);
    }

    private void setupFAB(){
        fabButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setupListBoxes();
                addWindow.open();
                animate(addWindow, Transition.FADEINDOWNBIG);
            }
        });
    }

    private void setupaddButton(){
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(checkifTimeisEmpty(startDatePicker)){
                    MaterialToast.fireToast("Start date is empty", "rounded");
                } else if(checkifTimeisEmpty(endDatePicker)){
                    MaterialToast.fireToast("End date is empty", "rounded");
                } else if(startDatePicker.getDate().getTime() >= endDatePicker.getDate().getTime()){
                    MaterialToast.fireToast("Start date cannot be bigger or equal than end date", "rounded");
                } else {
                    ReservationEntityDTO reservation = new ReservationEntityDTO();
                    reservation.setStart(startDatePicker.getDate());
                    reservation.setEnd(startDatePicker.getDate(), endDatePicker.getDate());
                    reservation.setGame(gameComboBox.getSingleValue());
                    reservation.setCustomer(customerComboBox.getSingleValue());
                    reservation.setPrice(getPrice(startDatePicker.getDate(),endDatePicker.getDate()));
                    reservations.add(reservation);
                    addReservation(reservation);
                    MaterialToast.fireToast("Reservation has been added", "rounded");
                    animate(addWindow, Transition.FADEOUT);
                    closeWindow(addWindow);

                    refreshTable();
                }

            }
        });
    }

    private int getPrice(Date pStart, Date pEnd){
        long start = TimeUnit.MILLISECONDS.toDays(pStart.getTime());
        long end = TimeUnit.MILLISECONDS.toDays(pEnd.getTime());
        return (int)(end-start) * 500;
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

    private void refreshTable(){
        dataTable.setVisibleRange(0,reservations.size());
        dataTable.setRowData(0,reservations);
        dataTable.getView().setRedraw(true);
        dataTable.getView().refresh();
    }

    private void setDataTable() {
        dataTable.getTableTitle().setText("Reservations");
        dataTable.getTableIcon().setIconType(IconType.CREATE);

        dataTable.addColumn(new TextColumn<ReservationEntityDTO>() {
            @Override
            public String getValue(ReservationEntityDTO reservationEntityDTO) {
                return "";
            }
        }, "");

        dataTable.addColumn(new TextColumn<ReservationEntityDTO>() {
            @Override
            public String getValue(ReservationEntityDTO reservationEntityDTO) {
                return String.valueOf(reservationEntityDTO.getGame().getName());
            }
        }, "Game");

        dataTable.addColumn(new TextColumn<ReservationEntityDTO>() {
            @Override
            public String getValue(ReservationEntityDTO reservationEntityDTO) {
                return String.valueOf(reservationEntityDTO.getCustomer().getName());
            }
        }, "Customer");

        dataTable.addColumn(new TextColumn<ReservationEntityDTO>() {
            @Override
            public String getValue(ReservationEntityDTO reservationEntityDTO) {
                String date = setupDate(reservationEntityDTO.getStart());
                return date;
            }
        }, "Start");

        dataTable.addColumn(new TextColumn<ReservationEntityDTO>() {
            @Override
            public String getValue(ReservationEntityDTO reservationEntityDTO) {
                String date = setupDate(reservationEntityDTO.getEnd());
                return date;
            }
        }, "End");

        dataTable.addColumn(new TextColumn<ReservationEntityDTO>() {
            @Override
            public String getValue(ReservationEntityDTO reservationEntityDTO) {
                return reservationEntityDTO.getPrice() + " Ft";
            }
        }, "Price");

        dataTable.addColumn(new WidgetColumn<ReservationEntityDTO, MaterialButton>() {

            @Override
            public MaterialButton getValue(ReservationEntityDTO reservationEntityDTO) {
                MaterialButton btn = new MaterialButton();
                btn.setSize(ButtonSize.MEDIUM);
                btn.setIconType(IconType.DELETE);
                btn.setWaves(WavesType.DEFAULT);
                btn.setBackgroundColor(Color.BLUE);
                btn.setType(ButtonType.FLOATING);
                btn.setTooltip("Delete this Reservation");
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

                                deleteReservation(reservationEntityDTO.getGame().getName());
                                reservations.remove(reservationEntityDTO);
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

    private void setupListBoxes(){
        getAllCustomer();
        getAllGame();

        for(CustomerEntityDTO customer : customers){
            customerComboBox.addItem(customer.getName(),customer);
        }

        for(GameEntityDTO game : games){
            if(!game.isOccupied()) {
                gameComboBox.addItem(game.getName(),game);
            }
        }
    }

    private void clearAddWindow(){
        customerComboBox.clear();
        gameComboBox.clear();
        startDatePicker.clear();
        endDatePicker.clear();
    }

    private void addReservation(ReservationEntityDTO pReservation){
        AsyncCallback<Void> reservationAsyncCallback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Void result) {
            }
        };
        reservationServiceAsync.addReservation(pReservation,reservationAsyncCallback);
    }

    public void getAllReservation(){
        AsyncCallback<List<ReservationEntityDTO>> reservationAsyncCallback = new AsyncCallback<List<ReservationEntityDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<ReservationEntityDTO> result) {
                reservations = result;
                dataTable.setVisibleRange(0,reservations.size());
                dataTable.setRowData(0,reservations);
            }
        };
        reservationServiceAsync.getAllReservation(reservationAsyncCallback);
    }

    public void getAllGame(){
        AsyncCallback<List<GameEntityDTO>> gameDTOAsyncCallback = new AsyncCallback<List<GameEntityDTO>>() {
            @Override
            public void onFailure(Throwable caught){
            }

            @Override
            public void onSuccess(List<GameEntityDTO> result) {
                games = result;
            }
        };

        gameServiceAsync.getAllGame(gameDTOAsyncCallback);
    }

    public void getAllCustomer(){
        AsyncCallback<List<CustomerEntityDTO>> customerEntityDTOAsyncCallback = new AsyncCallback<List<CustomerEntityDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<CustomerEntityDTO> result) {
                customers = result;
                GWT.log(String.valueOf(result.size()));
            }
        };

        customerServiceAsync.getAllCustomers(customerEntityDTOAsyncCallback);
    }

    private void deleteReservation(String pGameName){
        AsyncCallback<Void> reservationDTOAsyncCallBack = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Void result) {
                MaterialToast.fireToast("Reservation has been deleted", "rounded");
            }
        };
        reservationServiceAsync.deleteReservation(pGameName,reservationDTOAsyncCallBack);
    }
}
