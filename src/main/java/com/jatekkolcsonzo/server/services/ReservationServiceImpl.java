package com.jatekkolcsonzo.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jatekkolcsonzo.client.DTOEntity.ReservationEntityDTO;
import com.jatekkolcsonzo.client.services.ReservationService;
import com.jatekkolcsonzo.server.helper.DTOEntityHelper;
import com.jatekkolcsonzo.server.hibernate.dao.ReservationEntity_DAO;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.entities.ReservationEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
public class ReservationServiceImpl extends RemoteServiceServlet implements ReservationService {

    private ReservationEntity_DAO reservationDAO = new ReservationEntity_DAO();
    private DTOEntityHelper entityHelper = new DTOEntityHelper();

    @Override
    public void addReservation(ReservationEntityDTO pReservation) {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {
            @Override
            public void execute(EntityManager pEntityManager) {
                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        ReservationEntity reservationEntity = entityHelper.DTOtoEntity(pReservation);
                        reservationDAO.addReservation(
                                pEntityManager,
                                reservationEntity.getStart(),
                                reservationEntity.getEnd(),
                                reservationEntity.getCustomer(),
                                reservationEntity.getGame()
                        );
                    }
                };
            }
        };
    }

    @Override
    public ReservationEntityDTO getReservationByGameName(String pGameName) {
        ReservationEntity reservationEntity = new AbstractEntityManagerQuery<ReservationEntity>() {
            @Override
            public void execute(EntityManager pEntityManager) {
                result = reservationDAO.getReservationFromDBByGameName(pEntityManager,pGameName);
            }
        }.getResult();

        return entityHelper.entitytoDTO(reservationEntity);
    }

    @Override
    public List<ReservationEntityDTO> getAllReservation() {
        List<ReservationEntity> reservationEntityList = new AbstractEntityManagerQuery<List<ReservationEntity>>() {

            @Override
            public void execute(EntityManager pEntityManager) {
                result = reservationDAO.getReservationsFromDB(pEntityManager);
            }
        }.getResult();

        List<ReservationEntityDTO> reservationEntityDTOList = new ArrayList<>();

        for(ReservationEntity reservation : reservationEntityList){
            reservationEntityDTOList.add(entityHelper.entitytoDTO(reservation));
        }

        return reservationEntityDTOList;
    }

    @Override
    public void deleteReservation(String pGameName) {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {
                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        reservationDAO.deleteReservationFromDB(pEntityManager,pGameName);
                    }
                };
            }
        };
    }
}
