package org.example.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.beans.DataSourceConfiguration;
import org.example.entities.Book;

import java.net.URI;
import java.util.List;

@Path("/books")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    DataSourceConfiguration dataSource;

    @Context
    UriInfo uriInfo;

    public BookResource() {
    }

    @GET
    public Response getAll() {
        EntityManager em = this.dataSource.createEntityManager();
        List<Book> all = em.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();

        return Response.ok()
                .entity(all)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        EntityManager em = this.dataSource.createEntityManager();

        Book book = em.find(Book.class, id);
        if (book == null) {
            throw new NotFoundException("Book with id " + id + " not found");
        }

        return Response.ok()
                .entity(book)
                .build();
    }

    private void saveBook(Book book) {
        EntityManager em = this.dataSource.createEntityManager();

        em.getTransaction().begin();

        em.persist(book);
        //em.flush();

        em.getTransaction().commit();
    }

    @POST
    public Response create(@Valid Book book) {
        saveBook(book);

        final URI location = uriInfo.getBaseUriBuilder()
                .path(BookResource.class)
                .path(book.getId().toString())
                .build();

        return Response.created(location)
                .entity(book)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid Book book) {
        EntityManager em = this.dataSource.createEntityManager();

        Book existing = em.find(Book.class, id);
        if (existing == null) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        existing.setAuthor(book.getAuthor());
        existing.setTitle(book.getTitle());
        existing.setIsbn(book.getIsbn());
        existing.setPrice(book.getPrice());

        return Response.ok()
                .entity(existing)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        EntityManager em = this.dataSource.createEntityManager();

        Book book = em.find(Book.class, id);
        if (book == null) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        em.remove(book);

        return Response.noContent()
                .build();
    }
}