import {Component, Input, OnInit} from '@angular/core';
import {Logo} from './logo.model';

@Component( {
              selector: 'app-list-logos', templateUrl: './list-logos.component.html', styleUrls: ['./list-logos.component.css']
            } )
export class ListLogosComponent implements OnInit {
  @Input() s3: any;

  logos: Logo[] = [];

  constructor() {
  }

  ngOnInit() {
    this.listAlbums();
  }

  listAlbums() {
    const logos = this.logos;
    this.s3.listObjects( {Prefix: 'logos/'}, function( err, data ) {
      if ( err ) {
        return alert( 'There was an error viewing your album: ' + err.message );
      }
      const href = this.request.httpRequest.endpoint.href;
      const bucketUrl = href + 'rommelmarkt/';

      data.Contents.forEach( ( photo, i ) => {
        if(i == 0){
          return; //the first object is the directory itself
        }
        const photoKey = photo.Key;
        logos.push(new Logo( photoKey, bucketUrl + encodeURIComponent( photoKey ) ));
      } );

    } );

  }

}
