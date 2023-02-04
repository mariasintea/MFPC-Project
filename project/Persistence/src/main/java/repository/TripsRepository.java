package repository;

import domain.Trip;
import domain.HikesInTrip;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TripsRepository {
    SessionFactory sessionFactory;

    public TripsRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public int addTrip(Trip trip){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(trip);
                int id = (Integer) session.createQuery("select max(t.id) from Trip t").uniqueResult();
                tx.commit();
                return id;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return 0;
    }

    public void addHikeInTrip(HikesInTrip hikesInTrip){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(hikesInTrip);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public Double getTotal(Integer tripId){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                double total = (Double) session.createQuery("select sum(h.price * t.noPersons) from HikesInTrip t, Hike h where h.id = t.hikeId and t.tripId = :tripId").setParameter("tripId", tripId).uniqueResult();
                tx.commit();
                return total;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return 0.0;
    }
}
