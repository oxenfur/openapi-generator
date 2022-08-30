
/**
 * @interface Order
 */
export interface Order {

    /**
     * 
     * @type {number}
     * @memberof Order
     */
    id?: number;
    /**
     * 
     * @type {number}
     * @memberof Order
     */
    petId?: number;
    /**
     * 
     * @type {number}
     * @memberof Order
     */
    quantity?: number;
    /**
     * 
     * @type {String}
     * @memberof Order
     */
    shipDate?: String;
    /**
     * 
     * @type {String}
     * @memberof Order
     */
    status?: OrderStatusEnum;
    /**
     * 
     * @type {Boolean}
     * @memberof Order
     */
    complete?: Boolean;
}


/**
* @export
* @enum {string}
*/
export enum OrderStatusEnum {
    Placed = 'placed',
    Approved = 'approved',
    Delivered = 'delivered'
}
