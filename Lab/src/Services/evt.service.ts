import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Evt } from 'src/Models/Evt';

@Injectable({
  providedIn: 'root'
})
export class EvtService {
  
  private readonly GATEWAY_URL = 'http://localhost:9000';
  // IMPORTANT: Ajouter /api avant /evenements
  private readonly EVENT_API = `${this.GATEWAY_URL}/EVENEMENT-SERVICE/api/evenements`;

  constructor(private httpClient: HttpClient) { }

  // CRUD de base
  GetAllEvts(): Observable<Evt[]> {
    return this.httpClient.get<any>(this.EVENT_API).pipe(
      map(response => {
        console.log('Réponse brute du backend:', response);
        // Spring Data REST retourne les données dans _embedded.evenements
        if (response._embedded && response._embedded.evenements) {
          return response._embedded.evenements;
        }
        // Si c'est un tableau direct
        if (Array.isArray(response)) {
          return response;
        }
        return [];
      })
    );
  }

  saveEvent(evt: Evt): Observable<Evt> {
    return this.httpClient.post<Evt>(this.EVENT_API, evt);
  }

  getEvenementById(id: string): Observable<Evt> {
    return this.httpClient.get<Evt>(`${this.EVENT_API}/${id}`);
  }

  updateEvenement(id: string, evenement: Evt): Observable<Evt> {
    return this.httpClient.put<Evt>(`${this.EVENT_API}/${id}`, evenement);
  }

  deleteEvenement(id: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.EVENT_API}/${id}`);
  }

  // Méthodes de recherche
  findByTitreContaining(titre: string): Observable<Evt[]> {
    return this.httpClient.get<any>(`${this.EVENT_API}/search/findByTitreContaining?titre=${titre}`).pipe(
      map(response => response._embedded?.evenements || [])
    );
  }

  findByLieuContaining(lieu: string): Observable<Evt[]> {
    return this.httpClient.get<any>(`${this.EVENT_API}/search/findByLieuContaining?lieu=${lieu}`).pipe(
      map(response => response._embedded?.evenements || [])
    );
  }

  findUpcomingEvents(): Observable<Evt[]> {
    return this.httpClient.get<any>(`${this.EVENT_API}/search/findUpcomingEvents`).pipe(
      map(response => response._embedded?.evenements || [])
    );
  }

  findPastEvents(): Observable<Evt[]> {
    return this.httpClient.get<any>(`${this.EVENT_API}/search/findPastEvents`).pipe(
      map(response => response._embedded?.evenements || [])
    );
  }

  findAllByOrderByDateDesc(): Observable<Evt[]> {
    return this.httpClient.get<any>(`${this.EVENT_API}/search/findAllByOrderByDateDesc`).pipe(
      map(response => response._embedded?.evenements || [])
    );
  }
}