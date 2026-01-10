import { Component } from '@angular/core';

@Component({
  selector: 'app-tool',
  templateUrl: './tool.component.html',
  styleUrls: ['./tool.component.css']
})
export class ToolComponent {
 constructor() { }

  ngOnInit(): void {
  }

  addTool(): void {
    console.log('Ajouter un outil');
    // Implémentez la logique pour ajouter un outil
  }

  importTools(): void {
    console.log('Importer des outils');
    // Implémentez la logique d'import
  }
}