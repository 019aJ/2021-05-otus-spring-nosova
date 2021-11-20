import React from 'react'
import Grid from '@material-ui/core/Grid'

// Start Openlayers imports
import { 
    Map,
    View
 } from 'ol'
import {
    GeoJSON,
    XYZ,
	WKT
} from 'ol/format'
import {
    Tile as TileLayer,
    Vector as VectorLayer
} from 'ol/layer'
import {
    Vector as VectorSource,
    OSM as OSMSource,
    XYZ as XYZSource,
    TileWMS as TileWMSSource
} from 'ol/source'
import {
    Select as SelectInteraction,
    defaults as DefaultInteractions
} from 'ol/interaction'
import { 
    Attribution,
    ScaleLine,
    ZoomSlider,
    Zoom,
    Rotate,
    MousePosition,
    OverviewMap,
    defaults as DefaultControls
} from 'ol/control'
import {
    Style,
    Fill as FillStyle,
    RegularShape as RegularShapeStyle,
    Stroke as StrokeStyle,
	Icon
} from 'ol/style'

import { 
    Projection,
    get as getProjection
 } from 'ol/proj'

// End Openlayers imports

class OLMapFragment extends React.Component {
    constructor(props) {
        super(props)
        this.updateDimensions = this.updateDimensions.bind(this)
		this.state = {map: null, isLoading: true};

    }
    updateDimensions(){
        const h = window.innerWidth >= 992 ? window.innerHeight : 400
        this.setState({height: h})
    }
    componentWillMount(){
        window.addEventListener('resize', this.updateDimensions)
        this.updateDimensions()
    }
    componentDidMount(){
		var format = new WKT();
		var p1 = format.readFeature(
			'POINT(69.2582573 57.2893073)');
		p1.getGeometry().transform('EPSG:4326', 'EPSG:3857');
		var p2 = format.readFeature(
			'POINT(15974083.5 6970619.3)');
		var p3 = format.readFeature(
			'POINT(15981742.6 6930645.9)');
		var p4 = format.readFeature(
			'POINT(15992302.5 6695037.7)');

		const iconStyle = new Style({
		  image: new Icon({
			src: 'pngegg.png',
		  }),
		});
		p1.setStyle(iconStyle);
		p2.setStyle(iconStyle);
		p3.setStyle(iconStyle);
		p4.setStyle(iconStyle);

        // Create an Openlayer Map instance with two tile layers
        const map = new Map({
            //  Display the map in the div with the id of map
            target: 'map',
            layers: [
                new TileLayer({
                    source: new XYZSource({
                        url: 'https://{a-c}.tile.openstreetmap.org/{z}/{x}/{y}.png',
                        projection: 'EPSG:3857'
                    })
                }),
				new TileLayer({
					source: new XYZSource({
						url: 'http://tiles.openseamap.org/seamark/{z}/{x}/{y}.png',
						crossOrigin: null
					})
				}),
				new VectorLayer({
                    source: new VectorSource({
                        features: [p1,p2,p3,p4]
                    })
                })
            ],
            // Add in the following map controls
            controls: DefaultControls().extend([
                new ZoomSlider(),
                new MousePosition(),
                new ScaleLine(),
                new OverviewMap()
            ]),
            // Render the tile layers in a map view with a Mercator projection
            view: new View({
                projection: 'EPSG:3857',
                center: [15992116, 6695484],
                zoom: 9
            })
        })
		this.setState({map: map, isLoading: false});
    }
    componentWillUnmount(){
        window.removeEventListener('resize', this.updateDimensions)
    }
    render(){
	    const {map, isLoading} = this.state;

	
		if(!isLoading && this.props.dataFromParent !== null && this.props.dataFromParent !== undefined)
		{
			var format = new WKT();
			const monitoringsCoordList = this.props.dataFromParent.map(monitoring => {
				var feature = format.readFeature(monitoring.initialCoordinates);
				feature.getGeometry().transform('EPSG:4326', 'EPSG:3857');
				return new VectorLayer({
                    source: new VectorSource({
                        features: [feature]
					})});
				
			});
			map.getLayers().extend(monitoringsCoordList);
			const presictionCoordList = this.props.dataFromParent.map(monitoring => {
				var coordList = monitoring.predictionCoordinates.split(';');
				var features = coordList.map(poly=>{
					var feature = format.readFeature(poly);
					feature.getGeometry().transform('EPSG:4326', 'EPSG:3857');
					return feature;
				});
				var presictionLayer = new VectorLayer({
                    source: new VectorSource({
                        features: features
					})});
				presictionLayer.setStyle(new Style({
					  fill: new FillStyle({
						color: 'rgba(255, 100, 100, 0.3)'
					  }),
					  stroke: new StrokeStyle({
						color: 'black',
						width: 1.25
					  }),
					}));
				return presictionLayer
			});
			map.getLayers().extend(presictionCoordList);

		}
		
        const style = {
            width: '100%',
            height:'500px',
            backgroundColor: '#cccccc',
        }
        return (
            <Grid container>
                <Grid item xs={12}>
                    <div id='map' style={style} >
                    </div>
                </Grid>
            </Grid>
        )
    }
}
export default OLMapFragment