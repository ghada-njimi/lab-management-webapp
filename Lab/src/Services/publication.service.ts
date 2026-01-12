import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Publication } from 'src/Models/Publication';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
  
  private readonly GATEWAY_URL = 'http://localhost:9000';
  private readonly PUBLICATION_API = `${this.GATEWAY_URL}/PUBLICATION-SERVICE/api/publications`;

  constructor(private httpClient: HttpClient) { }

  // // CRUD de base (avec auteurs avant)
  // getAllPublications(): Observable<Publication[]> {
  //   return this.httpClient.get<any>(this.PUBLICATION_API).pipe(
  //     map(response => {
  //       console.log('Réponse publications:', response);
  //       if (response._embedded && response._embedded.publications) {
  //         return response._embedded.publications;
  //       }
  //       if (Array.isArray(response)) {
  //         return response;
  //       }
  //       return [];
  //     })
  //   );
  // }

  //getAllPublications modifié
  getAllPublications(): Observable<Publication[]> {
  return this.httpClient.get<any>(this.PUBLICATION_API).pipe(
    map(response => {
      const pubs = response._embedded?.publications || response || [];
      return pubs.map((p: any) => ({
        id: p.id,
        titre: p.titre,
        type: p.type,
        dateApparition: p.dateApparition || p.date || null,
        lien: p.lien,
        source: p.source || p.sourcePdf || null
      }));
    })
  );
}


  savePublication(publication: Publication): Observable<Publication> {
    const payload: any = { ...publication };
    // ensure date is sent as yyyy-MM-dd
    if (payload.dateApparition) {
      payload.dateApparition = this.formatDateToBackend(payload.dateApparition);
    }
    // be tolerant: include both field names if backend expects sourcePdf
    if (payload.source) {
      payload.sourcePdf = payload.source;
      payload.source = payload.source;
    }
    delete payload.id; // remove id for POST
    console.log('POST payload ->', payload);
    return this.httpClient.post<Publication>(this.PUBLICATION_API, payload);
  }

  getPublicationById(id: number): Observable<Publication> {
    return this.httpClient.get<any>(`${this.PUBLICATION_API}/${id}`).pipe(
      map((p: any) => ({
        id: p.id,
        titre: p.titre,
        type: p.type,
        dateApparition: p.dateApparition || p.date || null,
        lien: p.lien,
        source: p.source || p.sourcePdf || null
      }))
    );
  }

  updatePublication(id: number, publication: Publication): Observable<Publication> {
    const payload: any = { ...publication };
    if (payload.dateApparition) {
      payload.dateApparition = this.formatDateToBackend(payload.dateApparition);
    }
    if (payload.source) {
      payload.sourcePdf = payload.source;
      payload.source = payload.source;
    }
    console.log(`PUT payload for id=${id} ->`, payload);
    return this.httpClient.put<Publication>(`${this.PUBLICATION_API}/${id}`, payload);
  }

  private formatDateToBackend(date: any): string | null {
    if (!date) return null;
    if (typeof date === 'string') return date;
    if (date instanceof Date) {
      const y = date.getFullYear();
      const m = String(date.getMonth() + 1).padStart(2, '0');
      const d = String(date.getDate()).padStart(2, '0');
      return `${y}-${m}-${d}`;
    }
    return String(date);
  }

  deletePublication(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.PUBLICATION_API}/${id}`);
  }

  // Méthodes de recherche
  findByType(type: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByType?type=${type}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByTitreContaining(titre: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByTitreContaining?titre=${titre}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByDateApparitionBetween(startDate: string, endDate: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByDateApparitionBetween?start=${startDate}&end=${endDate}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByTypeAndYear(type: string, year: number): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByTypeAndYear?type=${type}&year=${year}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  findByTypeOrderByDateApparitionDesc(type: string): Observable<Publication[]> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/findByTypeOrderByDateApparitionDesc?type=${type}`
    ).pipe(
      map(response => response._embedded?.publications || [])
    );
  }

  // Statistiques
  countByType(type: string): Observable<number> {
    return this.httpClient.get<any>(
      `${this.PUBLICATION_API}/search/countByType?type=${type}`
    ).pipe(
      map(response => response || 0)
    );
  }
}