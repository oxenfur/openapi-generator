import { Category } from "./Category";
import { Tag } from "./Tag";

/**
 * @interface Pet
 */
export interface Pet {

    /**
     * 
     * @type {number}
     * @memberof Pet
     */
    id?: number;
    /**
     * 
     * @type {Category}
     * @memberof Pet
     */
    category?: Category;
    /**
     * 
     * @type {String}
     * @memberof Pet
     */
    name: String;
    /**
     * 
     * @type {Array<String>}
     * @memberof Pet
     */
    photoUrls: Array<String>;
    /**
     * 
     * @type {Array<Tag>}
     * @memberof Pet
     */
    tags?: Array<Tag>;
    /**
     * 
     * @type {String}
     * @memberof Pet
     */
    status?: PetStatusEnum;
}


/**
* @export
* @enum {string}
*/
export enum PetStatusEnum {
    Available = 'available',
    Pending = 'pending',
    Sold = 'sold'
}
