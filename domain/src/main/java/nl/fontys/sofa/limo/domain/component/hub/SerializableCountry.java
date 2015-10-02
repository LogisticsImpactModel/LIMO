package nl.fontys.sofa.limo.domain.component.hub;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright 2013 Stephen Samuel, https://github.com/sksamuel/gaia
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Changes made to project default: adding Implements Serializable
 *
 * @author Sven Mäurer
 */
public class SerializableCountry implements Comparable<SerializableCountry>, Serializable {
    private static final long serialVersionUID = 2740914735307544707L;

    private static List<SerializableCountry> all;

    public static final SerializableCountry Afghanistan = new SerializableCountry("Afghanistan", 4, "AF", "AFG", Continent.Asia);

    public static final SerializableCountry Albania = new SerializableCountry("Albania", 8, "AL", "ALB", Continent.Europe);
    public static final SerializableCountry Algeria = new SerializableCountry("Algeria", 12, "DZ", "DZA", Continent.Africa);
    public static final SerializableCountry AmericanSamoa = new SerializableCountry("American Samoa", 16, "AS", "ASM", Continent.Oceania);
    public static final SerializableCountry Andorra = new SerializableCountry("Andorra", 20, "AD", "AND", Continent.Europe);
    public static final SerializableCountry Angola = new SerializableCountry("Angola", 24, "AO", "AGO", Continent.Africa);
    public static final SerializableCountry Anguilla = new SerializableCountry("Anguilla", 660, "AI", "AIA", Continent.SouthAmerica);
    public static final SerializableCountry Antarctica = new SerializableCountry("Antarctica", 0, "AQ", "", Continent.Antartica);
    public static final SerializableCountry AntiguaBarbuda = new SerializableCountry("Antigua And Barbuda", 28, "AG", "ATG",
            Continent.SouthAmerica);
    public static final SerializableCountry Argentina = new SerializableCountry("Argentina", 32, "AR", "ARG", Continent.SouthAmerica);
    public static final SerializableCountry Armenia = new SerializableCountry("Armenia", 51, "AM", "ARM", Continent.Asia);
    public static final SerializableCountry Aruba = new SerializableCountry("Aruba", 533, "AW", "ABW", Continent.SouthAmerica);
    public static final SerializableCountry Australia = new SerializableCountry("Australia", 36, "AU", "AUS", Continent.Oceania);
    public static final SerializableCountry Austria = new SerializableCountry("Austria", 40, "AT", "AUT", Continent.Europe);
    public static final SerializableCountry Azerbaijan = new SerializableCountry("Azerbaijan", 31, "AZ", "AZE", Continent.Europe);
    public static final SerializableCountry Bahamas = new SerializableCountry("Bahamas", 44, "BS", "BHS", Continent.SouthAmerica);
    public static final SerializableCountry Bahrain = new SerializableCountry("Bahrain", 48, "BH", "BHR", Continent.Asia);
    public static final SerializableCountry Bangladesh = new SerializableCountry("Bangladesh", 50, "BD", "BGD", Continent.Asia);
    public static final SerializableCountry Barbados = new SerializableCountry("Barbados", 52, "BB", "BRB", Continent.SouthAmerica);
    public static final SerializableCountry Belarus = new SerializableCountry("Belarus", 112, "BY", "BLR", Continent.Europe);
    public static final SerializableCountry Belgium = new SerializableCountry("Belgium", 56, "BE", "BEL", Continent.Europe);
    public static final SerializableCountry Belize = new SerializableCountry("Belize", 84, "BZ", "BLZ", Continent.SouthAmerica);
    public static final SerializableCountry Benin = new SerializableCountry("Benin", 204, "BJ", "BEN", Continent.Africa);
    public static final SerializableCountry Bermuda = new SerializableCountry("Bermuda", 60, "BM", "BMU", Continent.SouthAmerica);
    public static final SerializableCountry Bhutan = new SerializableCountry("Bhutan", 64, "BT", "BTN", Continent.Asia);
    public static final SerializableCountry Bolivia = new SerializableCountry("Bolivia", 68, "BO", "BOL", Continent.SouthAmerica);
    public static final SerializableCountry BosniaHerzegovina = new SerializableCountry("Bosnia And Herzegovina", 70, "BA", "BIH", Continent.Europe);
    public static final SerializableCountry Botswana = new SerializableCountry("Botswana", 72, "BW", "BWA", Continent.Africa);
    public static final SerializableCountry BouvetIsland = new SerializableCountry("Bouvet Island", 0, "BV", "", Continent.Antartica);
    public static final SerializableCountry Brazil = new SerializableCountry("Brazil", 76, "BR", "BRA", Continent.SouthAmerica);
    public static final SerializableCountry BritishIndianOceanTerritory = new SerializableCountry("British Indian Ocean Territory", 0, "IO", "",
            Continent.Asia);
    public static final SerializableCountry BruneiDarussalam = new SerializableCountry("Brunei Darussalam", 96, "BN", "BRN", Continent.Asia);
    public static final SerializableCountry Bulgaria = new SerializableCountry("Bulgaria", 100, "BG", "BGR", Continent.Europe);
    public static final SerializableCountry BurkinaFaso = new SerializableCountry("Burkina Faso", 854, "BF", "BFA", Continent.Africa);
    public static final SerializableCountry Burundi = new SerializableCountry("Burundi", 108, "BI", "BDI", Continent.Africa);
    public static final SerializableCountry Cambodia = new SerializableCountry("Cambodia", 116, "KH", "KHM", Continent.Asia);
    public static final SerializableCountry Cameroon = new SerializableCountry("Cameroon", 120, "CM", "CMR", Continent.Africa);
    public static final SerializableCountry Canada = new SerializableCountry("Canada", 124, "CA", "CAN", Continent.NorthAmerica);
    public static final SerializableCountry CapeVerde = new SerializableCountry("Cape Verde", 132, "CV", "CPV", Continent.Africa);
    public static final SerializableCountry CaymanIslands = new SerializableCountry("Cayman Islands", 136, "KY", "CYM", Continent.SouthAmerica);
    public static final SerializableCountry CentralAfricanRepublic = new SerializableCountry("Central African Republic", 140, "CF", "CAF",
            Continent.Africa);
    public static final SerializableCountry Chad = new SerializableCountry("Chad", 148, "TD", "TCD", Continent.Africa);
    public static final SerializableCountry Chile = new SerializableCountry("Chile", 152, "CL", "CHL", Continent.SouthAmerica);
    public static final SerializableCountry China = new SerializableCountry("China", 156, "CN", "CHN", Continent.Asia);
    public static final SerializableCountry ChristmasIsland = new SerializableCountry("Christmas Island", 0, "CX", "", Continent.Oceania);
    public static final SerializableCountry CocosIslands = new SerializableCountry("Cocos Islands", 0, "CC", "", Continent.Oceania);
    public static final SerializableCountry Colombia = new SerializableCountry("Colombia", 170, "CO", "COL", Continent.SouthAmerica);
    public static final SerializableCountry Comoros = new SerializableCountry("Comoros", 174, "KM", "COM", Continent.Africa);
    public static final SerializableCountry Congo = new SerializableCountry("Congo", 178, "CG", "COG", Continent.Africa);
    public static final SerializableCountry DemocraticRepublicCongo = new SerializableCountry("Democratic Republic Of The Congo", 180, "CD", "COD",
            Continent.Africa);
    public static final SerializableCountry CookIslands = new SerializableCountry("Cook Islands", 184, "CK", "COK", Continent.Oceania);
    public static final SerializableCountry CostaRica = new SerializableCountry("Costa Rica", 188, "CR", "CRI", Continent.SouthAmerica);
    public static final SerializableCountry IvoryCoast = new SerializableCountry("Cote D'ivoire", 384, "CI", "CIV", Continent.Africa);
    public static final SerializableCountry Croatia = new SerializableCountry("Croatia", 191, "HR", "HRV", Continent.Europe);
    public static final SerializableCountry Cuba = new SerializableCountry("Cuba", 192, "CU", "CUB", Continent.SouthAmerica);
    public static final SerializableCountry Cyprus = new SerializableCountry("Cyprus", 196, "CY", "CYP", Continent.Europe);
    public static final SerializableCountry CzechRepublic = new SerializableCountry("Czech Republic", 203, "CZ", "CZE", Continent.Europe);
    public static final SerializableCountry Denmark = new SerializableCountry("Denmark", 208, "DK", "DNK", Continent.Europe);
    public static final SerializableCountry Djibouti = new SerializableCountry("Djibouti", 262, "DJ", "DJI", Continent.Africa);
    public static final SerializableCountry Dominica = new SerializableCountry("Dominica", 212, "DM", "DMA", Continent.SouthAmerica);
    public static final SerializableCountry DominicanRepublic = new SerializableCountry("Dominican Republic", 214, "DO", "DOM",
            Continent.SouthAmerica);
    public static final SerializableCountry Ecuador = new SerializableCountry("Ecuador", 218, "EC", "ECU", Continent.SouthAmerica);
    public static final SerializableCountry Egypt = new SerializableCountry("Egypt", 818, "EG", "EGY", Continent.Africa);
    public static final SerializableCountry ElSalvador = new SerializableCountry("El Salvador", 222, "SV", "SLV", Continent.SouthAmerica);
    public static final SerializableCountry EquatorialGuinea = new SerializableCountry("Equatorial Guinea", 226, "GQ", "GNQ", Continent.Africa);
    public static final SerializableCountry Eritrea = new SerializableCountry("Eritrea", 232, "ER", "ERI", Continent.Africa);
    public static final SerializableCountry Estonia = new SerializableCountry("Estonia", 233, "EE", "EST", Continent.Europe);
    public static final SerializableCountry Ethiopia = new SerializableCountry("Ethiopia", 231, "ET", "ETH", Continent.Africa);
    public static final SerializableCountry FalklandIslands = new SerializableCountry("Falkland Islands", 238, "FK", "FLK",
            Continent.SouthAmerica);
    public static final SerializableCountry FaroeIslands = new SerializableCountry("Faroe Islands", 234, "FO", "FRO", Continent.Europe);
    public static final SerializableCountry Fiji = new SerializableCountry("Fiji", 242, "FJ", "FJI", Continent.Oceania);
    public static final SerializableCountry Finland = new SerializableCountry("Finland", 246, "FI", "FIN", Continent.Europe);
    public static final SerializableCountry France = new SerializableCountry("France", 250, "FR", "FRA", Continent.Europe);
    public static final SerializableCountry FrenchGuiana = new SerializableCountry("French Guiana", 254, "GF", "GUF", Continent.SouthAmerica);
    public static final SerializableCountry FrenchPolynesia = new SerializableCountry("French Polynesia", 258, "PF", "PYF", Continent.Oceania);
    public static final SerializableCountry FrenchSouthernTerritories = new SerializableCountry("French Southern Territories", 0, "TF", "",
            Continent.Antartica);
    public static final SerializableCountry Gabon = new SerializableCountry("Gabon", 266, "GA", "GAB", Continent.Africa);
    public static final SerializableCountry Gambia = new SerializableCountry("Gambia", 270, "GM", "GMB", Continent.Africa);
    public static final SerializableCountry Georgia = new SerializableCountry("Georgia", 268, "GE", "GEO", Continent.Asia);
    public static final SerializableCountry Germany = new SerializableCountry("Germany", 276, "DE", "DEU", Continent.Europe);
    public static final SerializableCountry Ghana = new SerializableCountry("Ghana", 288, "GH", "GHA", Continent.Africa);
    public static final SerializableCountry Gibraltar = new SerializableCountry("Gibraltar", 292, "GI", "GIB", Continent.Europe);
    public static final SerializableCountry Greece = new SerializableCountry("Greece", 300, "GR", "GRC", Continent.Europe);
    public static final SerializableCountry Greenland = new SerializableCountry("Greenland", 304, "GL", "GRL", Continent.Europe);
    public static final SerializableCountry Grenada = new SerializableCountry("Grenada", 308, "GD", "GRD", Continent.SouthAmerica);
    public static final SerializableCountry Guadeloupe = new SerializableCountry("Guadeloupe", 312, "GP", "GLP", Continent.SouthAmerica);
    public static final SerializableCountry Guam = new SerializableCountry("Guam", 316, "GU", "GUM", Continent.Oceania);
    public static final SerializableCountry Guatemala = new SerializableCountry("Guatemala", 320, "GT", "GTM", Continent.SouthAmerica);
    public static final SerializableCountry Guinea = new SerializableCountry("Guinea", 324, "GN", "GIN", Continent.Africa);
    public static final SerializableCountry GuineaBissau = new SerializableCountry("Guinea-bissau", 624, "GW", "GNB", Continent.Africa);
    public static final SerializableCountry Guyana = new SerializableCountry("Guyana", 328, "GY", "GUY", Continent.SouthAmerica);
    public static final SerializableCountry Haiti = new SerializableCountry("Haiti", 332, "HT", "HTI", Continent.SouthAmerica);
    public static final SerializableCountry HeardMcdonald = new SerializableCountry("Heard Island And Mcdonald Islands", 0, "HM", "",
            Continent.Antartica);
    public static final SerializableCountry Vatican = new SerializableCountry("Holy See (Vatican)", 336, "VA", "VAT", Continent.Europe);
    public static final SerializableCountry Honduras = new SerializableCountry("Honduras", 340, "HN", "HND", Continent.SouthAmerica);
    public static final SerializableCountry HongKong = new SerializableCountry("Hong Kong", 344, "HK", "HKG", Continent.Asia);
    public static final SerializableCountry Hungary = new SerializableCountry("Hungary", 348, "HU", "HUN", Continent.Europe);
    public static final SerializableCountry Iceland = new SerializableCountry("Iceland", 352, "IS", "ISL", Continent.Europe);
    public static final SerializableCountry India = new SerializableCountry("India", 356, "IN", "IND", Continent.Asia);
    public static final SerializableCountry Indonesia = new SerializableCountry("Indonesia", 360, "ID", "IDN", Continent.Asia);
    public static final SerializableCountry Iran = new SerializableCountry("Iran", 364, "IR", "IRN", Continent.Asia);
    public static final SerializableCountry Iraq = new SerializableCountry("Iraq", 368, "IQ", "IRQ", Continent.Asia);
    public static final SerializableCountry Ireland = new SerializableCountry("Ireland", 372, "IE", "IRL", Continent.Europe);
    public static final SerializableCountry Israel = new SerializableCountry("Israel", 376, "IL", "ISR", Continent.Asia);
    public static final SerializableCountry Italy = new SerializableCountry("Italy", 380, "IT", "ITA", Continent.Europe);
    public static final SerializableCountry Jamaica = new SerializableCountry("Jamaica", 388, "JM", "JAM", Continent.SouthAmerica);
    public static final SerializableCountry Japan = new SerializableCountry("Japan", 392, "JP", "JPN", Continent.Asia);
    public static final SerializableCountry Jordan = new SerializableCountry("Jordan", 400, "JO", "JOR", Continent.Asia);
    public static final SerializableCountry Kazakhstan = new SerializableCountry("Kazakhstan", 398, "KZ", "KAZ", Continent.Asia);
    public static final SerializableCountry Kenya = new SerializableCountry("Kenya", 404, "KE", "KEN", Continent.Africa);
    public static final SerializableCountry Kiribati = new SerializableCountry("Kiribati", 296, "KI", "KIR", Continent.Oceania);
    public static final SerializableCountry NorthKorea = new SerializableCountry("North Korea", 408, "KP", "PRK", Continent.Asia);
    public static final SerializableCountry SouthKorea = new SerializableCountry("South Korea", 410, "KR", "KOR", Continent.Asia);
    public static final SerializableCountry Kuwait = new SerializableCountry("Kuwait", 414, "KW", "KWT", Continent.Asia);
    public static final SerializableCountry Kyrgyzstan = new SerializableCountry("Kyrgyzstan", 417, "KG", "KGZ", Continent.Asia);
    public static final SerializableCountry Laos = new SerializableCountry("Laos", 418, "LA", "LAO", Continent.Asia);
    public static final SerializableCountry Latvia = new SerializableCountry("Latvia", 428, "LV", "LVA", Continent.Europe);
    public static final SerializableCountry Lebanon = new SerializableCountry("Lebanon", 422, "LB", "LBN", Continent.Asia);
    public static final SerializableCountry Lesotho = new SerializableCountry("Lesotho", 426, "LS", "LSO", Continent.Africa);
    public static final SerializableCountry Liberia = new SerializableCountry("Liberia", 430, "LR", "LBR", Continent.Africa);
    public static final SerializableCountry Libya = new SerializableCountry("Libyan Arab Jamahiriya", 434, "LY", "LBY",
            Continent.Africa);
    public static final SerializableCountry Liechtenstein = new SerializableCountry("Liechtenstein", 438, "LI", "LIE", Continent.Europe);
    public static final SerializableCountry Lithuania = new SerializableCountry("Lithuania", 440, "LT", "LTU", Continent.Europe);
    public static final SerializableCountry Luxembourg = new SerializableCountry("Luxembourg", 442, "LU", "LUX", Continent.Europe);
    public static final SerializableCountry Macao = new SerializableCountry("Macao", 446, "MO", "MAC", Continent.Asia);
    public static final SerializableCountry Macedonia = new SerializableCountry("Macedonia", 807, "MK", "MKD", Continent.Europe);
    public static final SerializableCountry Madagascar = new SerializableCountry("Madagascar", 450, "MG", "MDG", Continent.Africa);
    public static final SerializableCountry Malawi = new SerializableCountry("Malawi", 454, "MW", "MWI", Continent.Africa);
    public static final SerializableCountry Malaysia = new SerializableCountry("Malaysia", 458, "MY", "MYS", Continent.Asia);
    public static final SerializableCountry Maldives = new SerializableCountry("Maldives", 462, "MV", "MDV", Continent.Asia);
    public static final SerializableCountry Mali = new SerializableCountry("Mali", 466, "ML", "MLI", Continent.Africa);
    public static final SerializableCountry Malta = new SerializableCountry("Malta", 470, "MT", "MLT", Continent.Europe);
    public static final SerializableCountry MarshallIslands = new SerializableCountry("Marshall Islands", 584, "MH", "MHL", Continent.Oceania);
    public static final SerializableCountry Martinique = new SerializableCountry("Martinique", 474, "MQ", "MTQ", Continent.Oceania);
    public static final SerializableCountry Mauritania = new SerializableCountry("Mauritania", 478, "MR", "MRT", Continent.Africa);
    public static final SerializableCountry Mauritius = new SerializableCountry("Mauritius", 480, "MU", "MUS", Continent.Africa);
    public static final SerializableCountry Mayotte = new SerializableCountry("Mayotte", 0, "YT", "", Continent.Africa);
    public static final SerializableCountry Mexico = new SerializableCountry("Mexico", 484, "MX", "MEX", Continent.NorthAmerica);
    public static final SerializableCountry Micronesia = new SerializableCountry("Micronesia", 583, "FM", "FSM", Continent.Oceania);
    public static final SerializableCountry Moldova = new SerializableCountry("Moldova, Republic Of", 498, "MD", "MDA", Continent.Europe);
    public static final SerializableCountry Monaco = new SerializableCountry("Monaco", 492, "MC", "MCO", Continent.Europe);
    public static final SerializableCountry Mongolia = new SerializableCountry("Mongolia", 496, "MN", "MNG", Continent.Asia);
    public static final SerializableCountry Montserrat = new SerializableCountry("Montserrat", 500, "MS", "MSR", Continent.SouthAmerica);
    public static final SerializableCountry Morocco = new SerializableCountry("Morocco", 504, "MA", "MAR", Continent.Africa);
    public static final SerializableCountry Mozambique = new SerializableCountry("Mozambique", 508, "MZ", "MOZ", Continent.Africa);
    public static final SerializableCountry Myanmar = new SerializableCountry("Myanmar", 104, "MM", "MMR", Continent.Asia);
    public static final SerializableCountry Namibia = new SerializableCountry("Namibia", 516, "NA", "NAM", Continent.Africa);
    public static final SerializableCountry Nauru = new SerializableCountry("Nauru", 520, "NR", "NRU", Continent.Oceania);
    public static final SerializableCountry Nepal = new SerializableCountry("Nepal", 524, "NP", "NPL", Continent.Asia);
    public static final SerializableCountry Netherlands = new SerializableCountry("Netherlands", 528, "NL", "NLD", Continent.Europe);
    public static final SerializableCountry NetherlandsAntilles = new SerializableCountry("Netherlands Antilles", 530, "AN", "ANT",
            Continent.SouthAmerica);
    public static final SerializableCountry NewCaledonia = new SerializableCountry("New Caledonia", 540, "NC", "NCL", Continent.Oceania);
    public static final SerializableCountry NewZealand = new SerializableCountry("New Zealand", 554, "NZ", "NZL", Continent.Oceania);
    public static final SerializableCountry Nicaragua = new SerializableCountry("Nicaragua", 558, "NI", "NIC", Continent.SouthAmerica);
    public static final SerializableCountry Niger = new SerializableCountry("Niger", 562, "NE", "NER", Continent.Africa);
    public static final SerializableCountry Nigeria = new SerializableCountry("Nigeria", 566, "NG", "NGA", Continent.Africa);
    public static final SerializableCountry Niue = new SerializableCountry("Niue", 570, "NU", "NIU", Continent.Oceania);
    public static final SerializableCountry NorfolkIsland = new SerializableCountry("Norfolk Island", 574, "NF", "NFK", Continent.Oceania);
    public static final SerializableCountry NorthernMarianaIslands = new SerializableCountry("Northern Mariana Islands", 580, "MP", "MNP",
            Continent.Oceania);
    public static final SerializableCountry Norway = new SerializableCountry("Norway", 578, "NO", "NOR", Continent.Europe);
    public static final SerializableCountry Oman = new SerializableCountry("Oman", 512, "OM", "OMN", Continent.Asia);
    public static final SerializableCountry Pakistan = new SerializableCountry("Pakistan", 586, "PK", "PAK", Continent.Asia);
    public static final SerializableCountry Palau = new SerializableCountry("Palau", 585, "PW", "PLW", Continent.Oceania);
    public static final SerializableCountry PalestinianTerritory = new SerializableCountry("Palestinian Territory", 0, "PS", "", Continent.Asia);
    public static final SerializableCountry Panama = new SerializableCountry("Panama", 591, "PA", "PAN", Continent.SouthAmerica);
    public static final SerializableCountry PapuaNewGuinea = new SerializableCountry("Papua New Guinea", 598, "PG", "PNG", Continent.Oceania);
    public static final SerializableCountry Paraguay = new SerializableCountry("Paraguay", 600, "PY", "PRY", Continent.SouthAmerica);
    public static final SerializableCountry Peru = new SerializableCountry("Peru", 604, "PE", "PER", Continent.SouthAmerica);
    public static final SerializableCountry Philippines = new SerializableCountry("Philippines", 608, "PH", "PHL", Continent.Asia);
    public static final SerializableCountry Pitcairn = new SerializableCountry("Pitcairn", 612, "PN", "PCN", Continent.Oceania);
    public static final SerializableCountry Poland = new SerializableCountry("Poland", 616, "PL", "POL", Continent.Europe);
    public static final SerializableCountry Portugal = new SerializableCountry("Portugal", 620, "PT", "PRT", Continent.Europe);
    public static final SerializableCountry PuertoRico = new SerializableCountry("Puerto Rico", 630, "PR", "PRI", Continent.SouthAmerica);
    public static final SerializableCountry Qatar = new SerializableCountry("Qatar", 634, "QA", "QAT", Continent.Asia);
    public static final SerializableCountry Reunion = new SerializableCountry("Reunion", 638, "RE", "REU", Continent.Africa);
    public static final SerializableCountry Romania = new SerializableCountry("Romania", 642, "RO", "ROM", Continent.Europe);
    public static final SerializableCountry Russia = new SerializableCountry("Russian Federation", 643, "RU", "RUS", Continent.Europe);
    public static final SerializableCountry Rwanda = new SerializableCountry("Rwanda", 646, "RW", "RWA", Continent.Africa);
    public static final SerializableCountry SaintHelena = new SerializableCountry("Saint Helena", 654, "SH", "SHN", Continent.Africa);
    public static final SerializableCountry SaintKittsNevis = new SerializableCountry("Saint Kitts And Nevis", 659, "KN", "KNA",
            Continent.SouthAmerica);
    public static final SerializableCountry SaintLucia = new SerializableCountry("Saint Lucia", 662, "LC", "LCA", Continent.SouthAmerica);
    public static final SerializableCountry SaintPierreMiquelon = new SerializableCountry("Saint Pierre And Miquelon", 666, "PM", "SPM",
            Continent.SouthAmerica);
    public static final SerializableCountry SaintVincentGrenadines = new SerializableCountry("Saint Vincent And The Grenadines", 670, "VC", "VCT",
            Continent.SouthAmerica);
    public static final SerializableCountry Samoa = new SerializableCountry("Samoa", 882, "WS", "WSM", Continent.Oceania);
    public static final SerializableCountry SanMarino = new SerializableCountry("San Marino", 674, "SM", "SMR", Continent.Europe);
    public static final SerializableCountry SaoTome = new SerializableCountry("Sao Tome And Principe", 678, "ST", "STP", Continent.Africa);
    public static final SerializableCountry SaudiArabia = new SerializableCountry("Saudi Arabia", 682, "SA", "SAU", Continent.Asia);
    public static final SerializableCountry Senegal = new SerializableCountry("Senegal", 686, "SN", "SEN", Continent.Africa);
    public static final SerializableCountry Seychelles = new SerializableCountry("Seychelles", 690, "SC", "SYC", Continent.Africa);
    public static final SerializableCountry SierraLeone = new SerializableCountry("Sierra Leone", 694, "SL", "SLE", Continent.Africa);
    public static final SerializableCountry Singapore = new SerializableCountry("Singapore", 702, "SG", "SGP", Continent.Asia);
    public static final SerializableCountry Slovakia = new SerializableCountry("Slovakia", 703, "SK", "SVK", Continent.Europe);
    public static final SerializableCountry Slovenia = new SerializableCountry("Slovenia", 705, "SI", "SVN", Continent.Europe);
    public static final SerializableCountry SolomonIslands = new SerializableCountry("Solomon Islands", 90, "SB", "SLB", Continent.Oceania);
    public static final SerializableCountry Somalia = new SerializableCountry("Somalia", 706, "SO", "SOM", Continent.Africa);
    public static final SerializableCountry SouthAfrica = new SerializableCountry("South Africa", 710, "ZA", "ZAF", Continent.Africa);
    public static final SerializableCountry SouthGeorgiaSandwich = new SerializableCountry("South Georgia And The South Sandwich Islands", 0, "GS",
            "", Continent.Antartica);
    public static final SerializableCountry Spain = new SerializableCountry("Spain", 724, "ES", "ESP", Continent.Europe);
    public static final SerializableCountry SriLanka = new SerializableCountry("Sri Lanka", 144, "LK", "LKA", Continent.Asia);
    public static final SerializableCountry Sudan = new SerializableCountry("Sudan", 736, "SD", "SDN", Continent.Africa);
    public static final SerializableCountry Suriname = new SerializableCountry("Suriname", 740, "SR", "SUR", Continent.SouthAmerica);
    public static final SerializableCountry SvalbardJanMayen = new SerializableCountry("Svalbard And Jan Mayen", 744, "SJ", "SJM",
            Continent.Europe);
    public static final SerializableCountry Swaziland = new SerializableCountry("Swaziland", 748, "SZ", "SWZ", Continent.Africa);
    public static final SerializableCountry Sweden = new SerializableCountry("Sweden", 752, "SE", "SWE", Continent.Europe);
    public static final SerializableCountry Switzerland = new SerializableCountry("Switzerland", 756, "CH", "CHE", Continent.Europe);
    public static final SerializableCountry Syria = new SerializableCountry("Syrian Arab Republic", 760, "SY", "SYR", Continent.Asia);
    public static final SerializableCountry Taiwan = new SerializableCountry("Taiwan", 158, "TW", "TWN", Continent.Asia);
    public static final SerializableCountry Tajikistan = new SerializableCountry("Tajikistan", 762, "TJ", "TJK", Continent.Asia);
    public static final SerializableCountry Tanzania = new SerializableCountry("Tanzania, United Republic Of", 834, "TZ", "TZA",
            Continent.Africa);
    public static final SerializableCountry Thailand = new SerializableCountry("Thailand", 764, "TH", "THA", Continent.Asia);
    public static final SerializableCountry Timor = new SerializableCountry("Timor-leste", 0, "TL", "", Continent.Asia);
    public static final SerializableCountry Togo = new SerializableCountry("Togo", 768, "TG", "TGO", Continent.Africa);
    public static final SerializableCountry Tokelau = new SerializableCountry("Tokelau", 772, "TK", "TKL", Continent.Oceania);
    public static final SerializableCountry Tonga = new SerializableCountry("Tonga", 776, "TO", "TON", Continent.Oceania);
    public static final SerializableCountry TrinidadTobago = new SerializableCountry("Trinidad And Tobago", 780, "TT", "TTO",
            Continent.SouthAmerica);
    public static final SerializableCountry Tunisia = new SerializableCountry("Tunisia", 788, "TN", "TUN", Continent.Africa);
    public static final SerializableCountry Turkey = new SerializableCountry("Turkey", 792, "TR", "TUR", Continent.Europe);
    public static final SerializableCountry Turkmenistan = new SerializableCountry("Turkmenistan", 795, "TM", "TKM", Continent.Asia);
    public static final SerializableCountry TurksCaicosIslands = new SerializableCountry("Turks And Caicos Islands", 796, "TC", "TCA",
            Continent.SouthAmerica);
    public static final SerializableCountry Tuvalu = new SerializableCountry("Tuvalu", 798, "TV", "TUV", Continent.Oceania);
    public static final SerializableCountry Uganda = new SerializableCountry("Uganda", 800, "UG", "UGA", Continent.Africa);
    public static final SerializableCountry Ukraine = new SerializableCountry("Ukraine", 804, "UA", "UKR", Continent.Europe);
    public static final SerializableCountry UAE = new SerializableCountry("United Arab Emirates", 784, "AE", "ARE", Continent.Asia);
    public static final SerializableCountry US = new SerializableCountry("United States", 840, "US", "USA", Continent.NorthAmerica);
    public static final SerializableCountry USMinorOutlyingIslands = new SerializableCountry("United States Minor Outlying Islands", 0, "UM", "",
            Continent.SouthAmerica);
    public static final SerializableCountry UK = new SerializableCountry("United Kingdom", 826, "GB", "GBR", Continent.Europe);
    public static final SerializableCountry Uruguay = new SerializableCountry("Uruguay", 858, "UY", "URY", Continent.SouthAmerica);
    public static final SerializableCountry Uzbekistan = new SerializableCountry("Uzbekistan", 860, "UZ", "UZB", Continent.Asia);
    public static final SerializableCountry Vanuatu = new SerializableCountry("Vanuatu", 548, "VU", "VUT", Continent.Oceania);
    public static final SerializableCountry Venezuela = new SerializableCountry("Venezuela", 862, "VE", "VEN", Continent.SouthAmerica);
    public static final SerializableCountry Vietnam = new SerializableCountry("Vietnam", 704, "VN", "VNM", Continent.Asia);
    public static final SerializableCountry BritishVirginIslands = new SerializableCountry("British Virgin Islands", 92, "VG", "VGB",
            Continent.SouthAmerica);
    public static final SerializableCountry USVirginIslands = new SerializableCountry("US Virgin Islands", 850, "VI", "VIR",
            Continent.SouthAmerica);
    public static final SerializableCountry WallisFutuna = new SerializableCountry("Wallis And Futuna", 876, "WF", "WLF", Continent.Oceania);
    public static final SerializableCountry WesternSahara = new SerializableCountry("Western Sahara", 732, "EH", "ESH", Continent.Africa);
    public static final SerializableCountry Yemen = new SerializableCountry("Yemen", 887, "YE", "YEM", Continent.Asia);
    public static final SerializableCountry Yugoslavia = new SerializableCountry("Yugoslavia", 891, "YU", "YUG", Continent.Europe);
    public static final SerializableCountry Zambia = new SerializableCountry("Zambia", 894, "ZM", "ZMB", Continent.Africa);
    public static final SerializableCountry Zimbabwe = new SerializableCountry("Zimbabwe", 716, "ZW", "ZWE", Continent.Africa);

    static {

        all = new ArrayList<SerializableCountry>();
        all.add(SerializableCountry.UK);
        all.add(SerializableCountry.Afghanistan);
        all.add(SerializableCountry.Albania);
        all.add(SerializableCountry.Algeria);
        all.add(SerializableCountry.AmericanSamoa);
        all.add(SerializableCountry.Andorra);
        all.add(SerializableCountry.Angola);
        all.add(SerializableCountry.Anguilla);
        all.add(SerializableCountry.Antarctica);
        all.add(SerializableCountry.AntiguaBarbuda);
        all.add(SerializableCountry.Argentina);
        all.add(SerializableCountry.Armenia);
        all.add(SerializableCountry.Aruba);
        all.add(SerializableCountry.Australia);
        all.add(SerializableCountry.Austria);
        all.add(SerializableCountry.Azerbaijan);
        all.add(SerializableCountry.Bahamas);
        all.add(SerializableCountry.Bahrain);
        all.add(SerializableCountry.Bangladesh);
        all.add(SerializableCountry.Barbados);
        all.add(SerializableCountry.Belarus);
        all.add(SerializableCountry.Belgium);
        all.add(SerializableCountry.Belize);
        all.add(SerializableCountry.Benin);
        all.add(SerializableCountry.Bermuda);
        all.add(SerializableCountry.Bhutan);
        all.add(SerializableCountry.Bolivia);
        all.add(SerializableCountry.BosniaHerzegovina);
        all.add(SerializableCountry.Botswana);
        all.add(SerializableCountry.BouvetIsland);
        all.add(SerializableCountry.Brazil);
        all.add(SerializableCountry.BritishIndianOceanTerritory);
        all.add(SerializableCountry.BruneiDarussalam);
        all.add(SerializableCountry.Bulgaria);
        all.add(SerializableCountry.BurkinaFaso);
        all.add(SerializableCountry.Burundi);
        all.add(SerializableCountry.Cambodia);
        all.add(SerializableCountry.Cameroon);
        all.add(SerializableCountry.Canada);
        all.add(SerializableCountry.CapeVerde);
        all.add(SerializableCountry.CaymanIslands);
        all.add(SerializableCountry.CentralAfricanRepublic);
        all.add(SerializableCountry.Chad);
        all.add(SerializableCountry.Chile);
        all.add(SerializableCountry.China);
        all.add(SerializableCountry.ChristmasIsland);
        all.add(SerializableCountry.CocosIslands);
        all.add(SerializableCountry.Colombia);
        all.add(SerializableCountry.Comoros);
        all.add(SerializableCountry.Congo);
        all.add(SerializableCountry.DemocraticRepublicCongo);
        all.add(SerializableCountry.CookIslands);
        all.add(SerializableCountry.CostaRica);
        all.add(SerializableCountry.IvoryCoast);
        all.add(SerializableCountry.Croatia);
        all.add(SerializableCountry.Cuba);
        all.add(SerializableCountry.Cyprus);
        all.add(SerializableCountry.CzechRepublic);
        all.add(SerializableCountry.Denmark);
        all.add(SerializableCountry.Djibouti);
        all.add(SerializableCountry.Dominica);
        all.add(SerializableCountry.DominicanRepublic);
        all.add(SerializableCountry.Ecuador);
        all.add(SerializableCountry.Egypt);
        all.add(SerializableCountry.ElSalvador);
        all.add(SerializableCountry.EquatorialGuinea);
        all.add(SerializableCountry.Eritrea);
        all.add(SerializableCountry.Estonia);
        all.add(SerializableCountry.Ethiopia);
        all.add(SerializableCountry.FalklandIslands);
        all.add(SerializableCountry.FaroeIslands);
        all.add(SerializableCountry.Fiji);
        all.add(SerializableCountry.Finland);
        all.add(SerializableCountry.France);
        all.add(SerializableCountry.FrenchGuiana);
        all.add(SerializableCountry.FrenchPolynesia);
        all.add(SerializableCountry.FrenchSouthernTerritories);
        all.add(SerializableCountry.Gabon);
        all.add(SerializableCountry.Gambia);
        all.add(SerializableCountry.Georgia);
        all.add(SerializableCountry.Germany);
        all.add(SerializableCountry.Ghana);
        all.add(SerializableCountry.Gibraltar);
        all.add(SerializableCountry.Greece);
        all.add(SerializableCountry.Greenland);
        all.add(SerializableCountry.Grenada);
        all.add(SerializableCountry.Guadeloupe);
        all.add(SerializableCountry.Guam);
        all.add(SerializableCountry.Guatemala);
        all.add(SerializableCountry.Guinea);
        all.add(SerializableCountry.GuineaBissau);
        all.add(SerializableCountry.Guyana);
        all.add(SerializableCountry.Haiti);
        all.add(SerializableCountry.HeardMcdonald);
        all.add(SerializableCountry.Vatican);
        all.add(SerializableCountry.Honduras);
        all.add(SerializableCountry.HongKong);
        all.add(SerializableCountry.Hungary);
        all.add(SerializableCountry.Iceland);
        all.add(SerializableCountry.India);
        all.add(SerializableCountry.Indonesia);
        all.add(SerializableCountry.Iran);
        all.add(SerializableCountry.Iraq);
        all.add(SerializableCountry.Ireland);
        all.add(SerializableCountry.Israel);
        all.add(SerializableCountry.Italy);
        all.add(SerializableCountry.Jamaica);
        all.add(SerializableCountry.Japan);
        all.add(SerializableCountry.Jordan);
        all.add(SerializableCountry.Kazakhstan);
        all.add(SerializableCountry.Kenya);
        all.add(SerializableCountry.Kiribati);
        all.add(SerializableCountry.NorthKorea);
        all.add(SerializableCountry.SouthKorea);
        all.add(SerializableCountry.Kuwait);
        all.add(SerializableCountry.Kyrgyzstan);
        all.add(SerializableCountry.Laos);
        all.add(SerializableCountry.Latvia);
        all.add(SerializableCountry.Lebanon);
        all.add(SerializableCountry.Lesotho);
        all.add(SerializableCountry.Liberia);
        all.add(SerializableCountry.Libya);
        all.add(SerializableCountry.Liechtenstein);
        all.add(SerializableCountry.Lithuania);
        all.add(SerializableCountry.Luxembourg);
        all.add(SerializableCountry.Macao);
        all.add(SerializableCountry.Macedonia);
        all.add(SerializableCountry.Madagascar);
        all.add(SerializableCountry.Malawi);
        all.add(SerializableCountry.Malaysia);
        all.add(SerializableCountry.Maldives);
        all.add(SerializableCountry.Mali);
        all.add(SerializableCountry.Malta);
        all.add(SerializableCountry.MarshallIslands);
        all.add(SerializableCountry.Martinique);
        all.add(SerializableCountry.Mauritania);
        all.add(SerializableCountry.Mauritius);
        all.add(SerializableCountry.Mayotte);
        all.add(SerializableCountry.Mexico);
        all.add(SerializableCountry.Micronesia);
        all.add(SerializableCountry.Moldova);
        all.add(SerializableCountry.Monaco);
        all.add(SerializableCountry.Mongolia);
        all.add(SerializableCountry.Montserrat);
        all.add(SerializableCountry.Morocco);
        all.add(SerializableCountry.Mozambique);
        all.add(SerializableCountry.Myanmar);
        all.add(SerializableCountry.Namibia);
        all.add(SerializableCountry.Nauru);
        all.add(SerializableCountry.Nepal);
        all.add(SerializableCountry.Netherlands);
        all.add(SerializableCountry.NetherlandsAntilles);
        all.add(SerializableCountry.NewCaledonia);
        all.add(SerializableCountry.NewZealand);
        all.add(SerializableCountry.Nicaragua);
        all.add(SerializableCountry.Niger);
        all.add(SerializableCountry.Nigeria);
        all.add(SerializableCountry.Niue);
        all.add(SerializableCountry.NorfolkIsland);
        all.add(SerializableCountry.NorthernMarianaIslands);
        all.add(SerializableCountry.Norway);
        all.add(SerializableCountry.Oman);
        all.add(SerializableCountry.Pakistan);
        all.add(SerializableCountry.Palau);
        all.add(SerializableCountry.PalestinianTerritory);
        all.add(SerializableCountry.Panama);
        all.add(SerializableCountry.PapuaNewGuinea);
        all.add(SerializableCountry.Paraguay);
        all.add(SerializableCountry.Peru);
        all.add(SerializableCountry.Philippines);
        all.add(SerializableCountry.Pitcairn);
        all.add(SerializableCountry.Poland);
        all.add(SerializableCountry.Portugal);
        all.add(SerializableCountry.PuertoRico);
        all.add(SerializableCountry.Qatar);
        all.add(SerializableCountry.Reunion);
        all.add(SerializableCountry.Romania);
        all.add(SerializableCountry.Russia);
        all.add(SerializableCountry.Rwanda);
        all.add(SerializableCountry.SaintHelena);
        all.add(SerializableCountry.SaintKittsNevis);
        all.add(SerializableCountry.SaintLucia);
        all.add(SerializableCountry.SaintPierreMiquelon);
        all.add(SerializableCountry.SaintVincentGrenadines);
        all.add(SerializableCountry.Samoa);
        all.add(SerializableCountry.SanMarino);
        all.add(SerializableCountry.SaoTome);
        all.add(SerializableCountry.SaudiArabia);
        all.add(SerializableCountry.Senegal);
        all.add(SerializableCountry.Seychelles);
        all.add(SerializableCountry.SierraLeone);
        all.add(SerializableCountry.Singapore);
        all.add(SerializableCountry.Slovakia);
        all.add(SerializableCountry.Slovenia);
        all.add(SerializableCountry.SolomonIslands);
        all.add(SerializableCountry.Somalia);
        all.add(SerializableCountry.SouthAfrica);
        all.add(SerializableCountry.SouthGeorgiaSandwich);
        all.add(SerializableCountry.Spain);
        all.add(SerializableCountry.SriLanka);
        all.add(SerializableCountry.Sudan);
        all.add(SerializableCountry.Suriname);
        all.add(SerializableCountry.SvalbardJanMayen);
        all.add(SerializableCountry.Swaziland);
        all.add(SerializableCountry.Sweden);
        all.add(SerializableCountry.Switzerland);
        all.add(SerializableCountry.Syria);
        all.add(SerializableCountry.Taiwan);
        all.add(SerializableCountry.Tajikistan);
        all.add(SerializableCountry.Tanzania);
        all.add(SerializableCountry.Thailand);
        all.add(SerializableCountry.Timor);
        all.add(SerializableCountry.Togo);
        all.add(SerializableCountry.Tokelau);
        all.add(SerializableCountry.Tonga);
        all.add(SerializableCountry.TrinidadTobago);
        all.add(SerializableCountry.Tunisia);
        all.add(SerializableCountry.Turkey);
        all.add(SerializableCountry.Turkmenistan);
        all.add(SerializableCountry.TurksCaicosIslands);
        all.add(SerializableCountry.Tuvalu);
        all.add(SerializableCountry.Uganda);
        all.add(SerializableCountry.Ukraine);
        all.add(SerializableCountry.UAE);
        all.add(SerializableCountry.US);
        all.add(SerializableCountry.USMinorOutlyingIslands);
        all.add(SerializableCountry.Uruguay);
        all.add(SerializableCountry.Uzbekistan);
        all.add(SerializableCountry.Vanuatu);
        all.add(SerializableCountry.Venezuela);
        all.add(SerializableCountry.Vietnam);
        all.add(SerializableCountry.BritishVirginIslands);
        all.add(SerializableCountry.USVirginIslands);
        all.add(SerializableCountry.WallisFutuna);
        all.add(SerializableCountry.WesternSahara);
        all.add(SerializableCountry.Yemen);
        all.add(SerializableCountry.Yugoslavia);
        all.add(SerializableCountry.Zambia);
        all.add(SerializableCountry.Zimbabwe);

        Collections.sort(all);

        all = Collections.unmodifiableList(all);

    }

    /**
     * Returns a list of all countries
     *
     */
    public static List<SerializableCountry> getAll() {
        return all;
    }

    /**
     * Returns a list of countries within the EU vat zone
     */
    public static List<SerializableCountry> getEuVatZone() {

        List<SerializableCountry> eu = new ArrayList();

        eu.add(SerializableCountry.UK);
        eu.add(SerializableCountry.Austria);
        eu.add(SerializableCountry.Belgium);
        eu.add(SerializableCountry.Cyprus);
        eu.add(SerializableCountry.CzechRepublic);
        eu.add(SerializableCountry.Denmark);
        eu.add(SerializableCountry.Estonia);
        eu.add(SerializableCountry.Finland);
        eu.add(SerializableCountry.France);
        eu.add(SerializableCountry.Germany);
        eu.add(SerializableCountry.Greece);
        eu.add(SerializableCountry.Hungary);
        eu.add(SerializableCountry.Ireland);
        eu.add(SerializableCountry.Italy);
        eu.add(SerializableCountry.Latvia);
        eu.add(SerializableCountry.Lithuania);
        eu.add(SerializableCountry.Luxembourg);
        eu.add(SerializableCountry.Malta);
        eu.add(SerializableCountry.Netherlands);
        eu.add(SerializableCountry.Poland);
        eu.add(SerializableCountry.Portugal);
        eu.add(SerializableCountry.Slovakia);
        eu.add(SerializableCountry.Slovenia);
        eu.add(SerializableCountry.Spain);
        eu.add(SerializableCountry.Sweden);

        return eu;
    }

    /**
     * Returns the country that matches the 2 or 3 character alpha code passed
     * in or null if no country matches.
     *
     */
    public static SerializableCountry getInstance(String code) {

        if (code == null || code.length() < 2 || code.length() > 3) {
            return null;
        }

        code = code.trim().toUpperCase();

        for (SerializableCountry country : all) {
            if (country.getIsoAlpha2().equals(code) || country.getIsoAlpha3().equals(code)) {
                return country;
            }
        }

        return null;
    }

    private final Continent continent;
    @Expose private final int isoNumber3;
    @Expose private final String name, isoAlpha2, isoAlpha3;

    protected SerializableCountry(String name, int number, String iso2, String iso3, Continent continent) {
        this.name = name;
        this.isoNumber3 = number;
        this.isoAlpha2 = iso2;
        this.isoAlpha3 = iso3;
        this.continent = continent;
    }

    /*
     * @see
     * java.lang.Comparable#compareTo(com.liferay.util.util.world.SerializableCountry)
     */
    @Override
    public int compareTo(SerializableCountry arg0) {

        if (this == arg0) {
            return 0;
        } else if (this == UK) {
            return -1;
        } else if (arg0 == UK) {
            return 1;
        }

        return getName().compareTo(arg0.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the Continent enum that is applicable for this country.
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * Returns the ISO 2 character alpha code for this country
     *
     * @return
     */
    public String getIsoAlpha2() {
        return isoAlpha2;
    }

    /**
     * Returns the ISO 3 character alpha code for this country
     *
     * @return
     */
    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    /**
     * Returns the ISO 3 digit number code for this country
     *
     * @return
     */
    public int getIsoNumber3() {
        return isoNumber3;
    }

    /**
     * Returns the name for this country
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public String getValue() {
        return getIsoAlpha3();
    }

    @Override
    public String toString() {
        return name;
    }
}
