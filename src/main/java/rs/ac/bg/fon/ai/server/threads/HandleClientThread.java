/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.threads;

import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.communication.Operations;
import rs.ac.bg.fon.ai.communication.communication.Receiver;
import rs.ac.bg.fon.ai.communication.communication.Request;
import rs.ac.bg.fon.ai.communication.communication.Response;
import rs.ac.bg.fon.ai.communication.communication.ResponseType;
import rs.ac.bg.fon.ai.communication.communication.Sender;
import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Employee;
import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.logic.Controller;

/**
 *
 * @author nkala
 */
public class HandleClientThread extends Thread {

    private Socket socket;
    private Employee loggedInEmployee; // Dodaj atribut za ulogovanog zaposlenog
    private static List<HandleClientThread> activeClients = new ArrayList<>(); // Lista aktivnih klijenata

    public HandleClientThread(Socket socket) {
        this.socket = socket;
        synchronized (activeClients) {
            activeClients.add(this);
        }
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            Request request;
            try {
                request = (Request) new Receiver(socket).receive();
                Response response = handleRequest(request);
                new Sender(socket).send(response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        synchronized (activeClients) {
            activeClients.remove(this);
        }
    }

    private Response handleRequest(Request request) {
        switch (request.getOperation()) {
            // Hall operations
            case Operations.SAVE_HALL:
                return saveHall(request);
            case Operations.GET_HALLS:
                return getHalls(request);
            case Operations.UPDATE_HALL:
                return updateHall(request);
            case Operations.DELETE_HALL:
                return deleteHall(request);
            case Operations.HAS_UPCOMING_PROJECTIONS_FOR_HALL:
                return hasUpcomingProjectionsForHall(request);

            // Projection operations
            case Operations.SAVE_PROJECTION:
                return saveProjection(request);
            case Operations.GET_ALL_PROJECTIONS:
                return getAllProjections(request);
            case Operations.GET_PROJECTION_BY_ID:
                return getProjectionById(request);
            case Operations.GET_PROJECTIONS_BY_DATE:
                return getProjectionsByDate(request);
            case Operations.UPDATE_PROJECTION:
                return updateProjection(request);
            case Operations.EDIT_PROJECTION:
                return editProjection(request);
            case Operations.DELETE_PROJECTION:
                return deleteProjection(request);

            // Film operations
            case Operations.ADD_MOVIE:
                return addMovie(request);
            case Operations.GET_ALL_FILMS:
                return getAllFilms(request);
            case Operations.GET_GENRES_BY_ID:
                return getGenresByID(request);

            // Genre operations
            case Operations.GET_ALL_GENRES:
                return getAllGenres(request);

            // Ticket operations
            case Operations.SAVE_TICKETS:
                return saveTickets(request);
            case Operations.GET_TICKETS_BY_BILL_ID:
                return getTicketsByBillId(request);

            // Bill operations
            case Operations.SAVE_BILL:
                return saveBill(request);
            case Operations.GET_ALL_BILLS:
                return getAllBills(request);
            case Operations.EDIT_BILL:
                return editBill(request);

            case Operations.LOGIN:
                return login(request);

            default:
                return createErrorResponse("Unknown operation: " + request.getOperation());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    private Response createErrorResponse(String message) {
        Response response = new Response();
        response.setResponseType(ResponseType.ERROR);
        response.setException(new Exception(message));
        return response;
    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public static List<HandleClientThread> getActiveClients() {
        synchronized (activeClients) {
            return new ArrayList<>(activeClients); // Vrati kopiju liste
        }
    }

    private Response login(Request request) {
        Response response = new Response();
        try {
            // Parametar će biti niz [username, password] ili LoginParams objekat
            Object[] loginParams = (Object[]) request.getArgument();
            String username = (String) loginParams[0];
            String password = (String) loginParams[1];

            Employee employee = Controller.getInstance().login(username, password);
            this.loggedInEmployee = employee;

            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(employee);
            System.out.println("Successful LOGIN operation for user: " + username);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    // Hall operations implementation
    private Response saveHall(Request request) {
        Response response = new Response();
        try {
            Hall hall = (Hall) request.getArgument();
            Controller.getInstance().saveHall(hall);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(hall);
            System.out.println("Successful SAVE_HALL operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getHalls(Request request) {
        Response response = new Response();
        try {
            List<Hall> halls = Controller.getInstance().getHalls();
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(halls);
            System.out.println("Successful GET_HALLS operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response updateHall(Request request) {
        Response response = new Response();
        try {
            Hall hall = (Hall) request.getArgument();
            Controller.getInstance().updateHall(hall);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(hall);
            System.out.println("Successful UPDATE_HALL operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response deleteHall(Request request) {
        Response response = new Response();
        try {
            Hall hall = (Hall) request.getArgument();
            Controller.getInstance().deleteHall(hall);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(null);
            System.out.println("Successful DELETE_HALL operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response hasUpcomingProjectionsForHall(Request request) {
        Response response = new Response();
        try {
            Hall hall = (Hall) request.getArgument();
            boolean hasUpcoming = Controller.getInstance().hasUpcomingProjectionsForHall(hall);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(hasUpcoming);
            System.out.println("Successful HAS_UPCOMING_PROJECTIONS_FOR_HALL operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    // Projection operations implementation
    private Response saveProjection(Request request) {
        Response response = new Response();
        try {
            Projection projection = (Projection) request.getArgument();
            Projection savedProjection = Controller.getInstance().saveProjection(projection);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(savedProjection);
            System.out.println("Successful SAVE_PROJECTION operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getAllProjections(Request request) {
        Response response = new Response();
        try {
            List<Projection> projections = Controller.getInstance().getAllProjections();
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(projections);
            System.out.println("Successful GET_ALL_PROJECTIONS operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getProjectionById(Request request) {
        Response response = new Response();
        try {
            Long projectionId = (Long) request.getArgument();
            Projection projection = Controller.getInstance().getProjectionById(projectionId);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(projection);
            System.out.println("Successful GET_PROJECTION_BY_ID operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getProjectionsByDate(Request request) {
        Response response = new Response();
        try {
            LocalDate date = (LocalDate) request.getArgument();
            List<Projection> projections = Controller.getInstance().getProjectionsByDate(date);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(projections);
            System.out.println("Successful GET_PROJECTIONS_BY_DATE operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response updateProjection(Request request) {
        Response response = new Response();
        try {
            Projection projection = (Projection) request.getArgument();
            Controller.getInstance().updateProjection(projection);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(null);
            System.out.println("Successful UPDATE_PROJECTION_SOLD_TICKETS operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response editProjection(Request request) {
        Response response = new Response();
        try {
            Projection projection = (Projection) request.getArgument();
            Controller.getInstance().editProjection(projection);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(projection);
            System.out.println("Successful EDIT_PROJECTION operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response deleteProjection(Request request) {
        Response response = new Response();
        try {
            Projection projection = (Projection) request.getArgument();
            Controller.getInstance().deleteProjection(projection);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(null);
            System.out.println("Successful DELETE_PROJECTION operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    // Film operations implementation
    private Response addMovie(Request request) {
        Response response = new Response();
        try {
            Film film = (Film) request.getArgument();
            Controller.getInstance().addMovie(film);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(film);
            System.out.println("Successful ADD_MOVIE operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getAllFilms(Request request) {
        Response response = new Response();
        try {
            List<Film> films = Controller.getInstance().getAllFilms();
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(films);
            System.out.println("Successful GET_ALL_FILMS operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getGenresByID(Request request) {
        Response response = new Response();
        try {
            Long filmId = (Long) request.getArgument();
            List<Genre> genres = Controller.getInstance().getGenresByID(filmId);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(genres);
            System.out.println("Successful GET_GENRES_BY_ID operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    // Genre operations implementation
    private Response getAllGenres(Request request) {
        Response response = new Response();
        try {
            List<Genre> genres = Controller.getInstance().getAllGenres();
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(genres);
            System.out.println("Successful GET_ALL_GENRES operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    // Ticket operations implementation
    private Response saveTickets(Request request) {
        Response response = new Response();
        try {
            List<Ticket> tickets = (List<Ticket>) request.getArgument();
            Controller.getInstance().saveTickets(tickets);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(null);
            System.out.println("Successful SAVE_TICKETS operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getTicketsByBillId(Request request) {
        Response response = new Response();
        try {
            Long billId = (Long) request.getArgument();
            List<Ticket> tickets = Controller.getInstance().getTicketsByBillId(billId);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(tickets);
            System.out.println("Successful GET_TICKETS_BY_BILL_ID operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    // Bill operations implementation
    private Response saveBill(Request request) {
        Response response = new Response();
        try {
            Bill bill = (Bill) request.getArgument();
            Bill savedBill = Controller.getInstance().saveBill(bill);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(savedBill);
            System.out.println("Successful SAVE_BILL operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response editBill(Request request) {
        Response response = new Response();
        try {
            Bill bill = (Bill) request.getArgument();
            Controller.getInstance().editBill(bill);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(bill);
            System.out.println("Successful EDIT_BILL operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getAllBills(Request request) {
        Response response = new Response();
        try {
            List<Bill> bills = Controller.getInstance().getAllBills();
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(bills);
            System.out.println("Successful GET_ALL_BILLS operation");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }
}
