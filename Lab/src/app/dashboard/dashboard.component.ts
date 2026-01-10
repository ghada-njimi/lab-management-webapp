import { Component } from '@angular/core';
import { EvtService } from 'src/Services/evt.service';
import { MemberService } from 'src/Services/member.service';
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
      // ⤵️ Add these
      label: '$ in millions',
      data: [ 1551, 1688, 1800, 1895, 2124, 2124 ]
    }
  ];
  chartLabels: string[] = ['A','B','C','D','E','F'];
  chartOptions: ChartOptions = {};

  // other code
  constructor(private Ms:MemberService,private ES:EvtService){
    this.Ms.GetAllMembers().subscribe((res)=>{
      this.Nb_Membres=res.length
    })
     this.ES.GetAllEvts().subscribe((res)=>{
      this.Nb_Evenements=res.length
    })
  }
}