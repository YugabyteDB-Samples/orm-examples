<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class OrderLines extends Model
{
    public $timestamps = false;
    // protected $primaryKey = 'line_id';
    use HasFactory;

    public function orders() 
    {
        return $this->belongsTo(Orders::class);

    }

    public function products()
    {
        return $this->belongsTo(Products::class);
    }
}
