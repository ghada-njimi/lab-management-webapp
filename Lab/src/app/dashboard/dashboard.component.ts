import { Component } from '@angular/core';
import { EvtService } from 'src/Services/evt.service';
import { MemberService } from 'src/Services/member.service';
import { ToolService } from 'src/Services/tool.service';
import { PublicationService } from 'src/Services/publication.service';
import { ChartDataset, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent  {
  Nb_Membres: number = 0;
  Nb_Evenements: number = 0;
  Nb_Outils: number = 0;
  Nb_Articles: number = 0;
  chartData: ChartDataset[] = [
    {
      label: 'Nombre d\'Étudiants',
      data: [ 150, 175, 190, 210, 225, 240 ],
      borderColor: '#10b981',
      backgroundColor: 'rgba(16, 185, 129, 0.1)',
      pointBackgroundColor: '#10b981',
      pointBorderColor: '#10b981',
      pointRadius: 5,
      tension: 0.3
    }
  ];
  chartLabels: string[] = ['12 Pub', '18 Pub', '25 Pub', '32 Pub', '40 Pub', '45 Pub'];
  chartOptions: ChartOptions = {};

  // other code
  constructor(private Ms:MemberService, private ES:EvtService, private TS:ToolService, private PS:PublicationService){
    this.Ms.GetAllMembers().subscribe((res)=>{
      this.Nb_Membres=res.length
    })
    this.ES.GetAllEvts().subscribe((res)=>{
      this.Nb_Evenements=res.length
    })
    this.TS.getAllOutils().subscribe((res)=>{
      this.Nb_Outils=res.length
    })
    this.PS.getAllPublications().subscribe((res)=>{
      this.Nb_Articles=res.length
    })
  }
}