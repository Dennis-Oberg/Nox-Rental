export interface RentalRequest {
    carId: number,
    driverName: string,
    pickUpDate: Date,
    returnDate: Date,
    driverAge: number
}

export interface APIResponse {
    message: string;
    statusCode: number;
}


export interface CarInfo {
    id: number,
    carName: string;
    pricePerDay: number;
}

export interface Rental {
    id: number,
    pickUpDate: Date,
    returnDate: Date,
    car: CarInfo,
    totalRentalCost: number,
    driverName: string
}

export interface RentalInfo {
    rentals: Rental[],
    totalRevenue: number
}