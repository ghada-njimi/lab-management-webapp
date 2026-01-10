import { Component, ViewChild, OnInit } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from 'src/Services/auth.service';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.css']
})
export class TemplateComponent implements OnInit {
  @ViewChild('drawer') sidenav!: MatSidenav;
  sidenavOpened = false;
  
  currentPageTitle = 'Tableau de bord';

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.setupRouterEvents();
  }

  private setupRouterEvents(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.updatePageTitle();
    });
  }

  private updatePageTitle(): void {
    let route = this.activatedRoute.firstChild;
    
    while (route?.firstChild) {
      route = route.firstChild;
    }
    
    // Titres correspondant à vos routes
    const routes: {[key: string]: string} = {
      'dashboard': 'Tableau de bord',
      'members': 'Gestion des Membres',
      'create': 'Ajouter un membre',
      'events': 'Gestion des Événements',
      'tools': 'Gestion des Outils',  // Ajouté
      'publications': 'Gestion des Publications'  // Ajouté
    };
    
    if (route?.snapshot.url[0]) {
      const path = route.snapshot.url[0].path;
      this.currentPageTitle = routes[path] || 'Laboratoire de Recherche';
    }
  }

  toggleSidenav(): void {
    this.sidenavOpened = !this.sidenavOpened;
    if (this.sidenav) {
      this.sidenav.toggle();
    }
  }

  onNavLinkClick(): void {
    if (window.innerWidth < 1024) {
      this.sidenavOpened = false;
      if (this.sidenav) {
        this.sidenav.close();
      }
    }
  }

  logout(): void {
    this.authService.signOut().then(() => {
      this.router.navigate(['/login']);
    }).catch(error => {
      console.error('Erreur de déconnexion:', error);
    });
  }
}