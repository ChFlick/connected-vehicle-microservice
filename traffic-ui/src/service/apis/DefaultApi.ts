/* tslint:disable */
/* eslint-disable */
/**
 * Public Transport API
 * This API allows CRUD operations on SUMO Traffic data
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import {
    Vehicle,
    VehicleFromJSON,
    VehicleToJSON,
} from '../models';

export interface PublicTransportBusesBetweenGetRequest {
    end: Date;
    start: Date;
}

export interface PublicTransportBusesMeanDataBetweenGetRequest {
    end: Date;
    start: Date;
    meanBy?: string;
}

/**
 * no description
 */
export class DefaultApi extends runtime.BaseAPI {

    /**
     */
    async publicTransportBusesBetweenGetRaw(requestParameters: PublicTransportBusesBetweenGetRequest): Promise<runtime.ApiResponse<Array<Vehicle>>> {
        if (requestParameters.end === null || requestParameters.end === undefined) {
            throw new runtime.RequiredError('end','Required parameter requestParameters.end was null or undefined when calling publicTransportBusesBetweenGet.');
        }

        if (requestParameters.start === null || requestParameters.start === undefined) {
            throw new runtime.RequiredError('start','Required parameter requestParameters.start was null or undefined when calling publicTransportBusesBetweenGet.');
        }

        const queryParameters: runtime.HTTPQuery = {};

        if (requestParameters.end !== undefined) {
            queryParameters['end'] = (requestParameters.end as any).toISOString();
        }

        if (requestParameters.start !== undefined) {
            queryParameters['start'] = (requestParameters.start as any).toISOString();
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/public-transport/buses/between`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        });

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(VehicleFromJSON));
    }

    /**
     */
    async publicTransportBusesBetweenGet(requestParameters: PublicTransportBusesBetweenGetRequest): Promise<Array<Vehicle>> {
        const response = await this.publicTransportBusesBetweenGetRaw(requestParameters);
        return await response.value();
    }

    /**
     */
    async publicTransportBusesMeanDataBetweenGetRaw(requestParameters: PublicTransportBusesMeanDataBetweenGetRequest): Promise<runtime.ApiResponse<Array<Vehicle>>> {
        if (requestParameters.end === null || requestParameters.end === undefined) {
            throw new runtime.RequiredError('end','Required parameter requestParameters.end was null or undefined when calling publicTransportBusesMeanDataBetweenGet.');
        }

        if (requestParameters.start === null || requestParameters.start === undefined) {
            throw new runtime.RequiredError('start','Required parameter requestParameters.start was null or undefined when calling publicTransportBusesMeanDataBetweenGet.');
        }

        const queryParameters: runtime.HTTPQuery = {};

        if (requestParameters.end !== undefined) {
            queryParameters['end'] = (requestParameters.end as any).toISOString();
        }

        if (requestParameters.meanBy !== undefined) {
            queryParameters['meanBy'] = requestParameters.meanBy;
        }

        if (requestParameters.start !== undefined) {
            queryParameters['start'] = (requestParameters.start as any).toISOString();
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/public-transport/buses/mean-data-between`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        });

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(VehicleFromJSON));
    }

    /**
     */
    async publicTransportBusesMeanDataBetweenGet(requestParameters: PublicTransportBusesMeanDataBetweenGetRequest): Promise<Array<Vehicle>> {
        const response = await this.publicTransportBusesMeanDataBetweenGetRaw(requestParameters);
        return await response.value();
    }

}
