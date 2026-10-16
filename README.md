# PowerView

PowerView is a data field for the Hammerhead Karoo. It shows power in large type, plus two smaller power metrics that you choose, and fills the background with the color of the power zone you are in.

Open the PowerView app on the Karoo to choose those three values. The choice is saved as soon as you pick it.

## What you will see

- The large number is the power metric you chose for the big value. By default it is your 3-second power, in watts.
- The upper-right number is the metric you chose for the top corner. By default it is the maximum power of the activity you are recording. Its short label is **POT MÁX**.
- The lower-right number is the metric you chose for the bottom corner. By default it is the average power of that same activity. Its short label is **POT MED**.
- The bolt icon at the top left marks the field.
- The background is the color of your power zone. The text switches between light and dark so it stays readable.

| Zone | Background | Text |
| --- | --- | --- |
| No data | White | Dark |
| Z1, active recovery | Dark green | Light |
| Z2, endurance | Light green | Dark |
| Z3, tempo | Yellow | Dark |
| Z4, threshold | Salmon | Dark |
| Z5, VO2max | Orange | Light |
| Z6, anaerobic | Red | Light |
| Z7, neuromuscular | Purple | Light |

Those zones are the ones on your Karoo profile. PowerView uses the zone the Karoo has already assigned to your power. The color follows that zone even when the large number is a smoothed power, an average, or another metric.

## The three values

The PowerView app has one screen, titled **PowerView — Configuración**. It has three rows. Each row is a dropdown. There is no save button: the Karoo stores the choice the moment you select it, including during a ride. The field on the data page picks up the new choice on its next reading.

| Row on the screen | What it controls |
| --- | --- |
| **Valor grande** | The large number. Only current power and the short smoothed powers are offered here. |
| **Superior** | The smaller number at the upper right, with its short label above the value. |
| **Inferior** | The smaller number at the lower right, with its short label above the value. |

**Superior** and **Inferior** start with **Sin usar**. That choice leaves the corner empty. **Valor grande** always shows a power value.

The sentence under the title says that the large value only accepts current or smoothed power, and that the upper and lower rows accept any power field. The names in the dropdowns are in Spanish. This is what each one means, and the short label printed on the field:

| Name in the dropdown | Label on the field | What it shows | Where you can put it |
| --- | --- | --- | --- |
| Potencia actual | POT | Power right now, in watts | Large, upper, or lower |
| Potencia (3s) | POT 3S | Power smoothed over 3 seconds | Large, upper, or lower |
| Potencia (5s) | POT 5S | Power smoothed over 5 seconds | Large, upper, or lower |
| Potencia (10s) | POT 10S | Power smoothed over 10 seconds | Large, upper, or lower |
| Potencia (30s) | POT 30S | Power smoothed over 30 seconds | Upper or lower |
| Potencia (20 min) | POT 20M | Power smoothed over 20 minutes | Upper or lower |
| Potencia (1 hora) | POT 1H | Power smoothed over 1 hour | Upper or lower |
| Potencia media (actividad) | POT MED | Average power since you pressed record | Upper or lower |
| Potencia máxima (actividad) | POT MÁX | Highest power since you pressed record | Upper or lower |
| Potencia media (esta vuelta) | POT MED L | Average power of the lap you are on | Upper or lower |
| Potencia máxima (esta vuelta) | POT MÁX L | Highest power of the lap you are on | Upper or lower |
| Potencia media (vuelta ant.) | POT MED VA | Average power of the lap you just finished | Upper or lower |
| Potencia máxima (vuelta ant.) | POT MÁX VA | Highest power of the lap you just finished | Upper or lower |
| Potencia normalizada (actividad) | NP | Normalized power of this activity | Upper or lower |
| Pot. normalizada (esta vuelta) | NP L | Normalized power of the current lap | Upper or lower |
| Pot. normalizada (vuelta ant.) | NP VA | Normalized power of the previous lap | Upper or lower |
| Potencia/peso (actividad) | W/KG | Watts per kilogram for this activity, from your weight on the Karoo | Upper or lower |
| Potencia/peso (esta vuelta) | W/KG L | Watts per kilogram for the current lap | Upper or lower |
| Potencia/peso (vuelta ant.) | W/KG VA | Watts per kilogram for the previous lap | Upper or lower |
| Zona de potencia | Z.POT | The zone number the Karoo has assigned | Upper or lower |
| % FTP | %FTP | Your power as a percentage of the FTP on your Karoo | Upper or lower |
| Intensity Factor | IF | Intensity factor of this activity | Upper or lower |
| Par motor medio (actividad) | PAR MED | Average torque for this activity | Upper or lower |
| Par motor máximo (actividad) | PAR MÁX | Highest torque for this activity | Upper or lower |
| Par motor máximo (esta vuelta) | PAR MÁX L | Highest torque of the current lap | Upper or lower |
| Par motor (3s) | PAR 3S | Torque smoothed over 3 seconds | Upper or lower |
| Balance de pedaleo | BAL | Left/right pedal balance right now | Upper or lower |
| Balance pedaleo medio (actividad) | BAL MED | Average pedal balance for this activity | Upper or lower |
| Balance pedaleo (3s) | BAL 3S | Pedal balance smoothed over 3 seconds | Upper or lower |
| Balance pedaleo (10s) | BAL 10S | Pedal balance smoothed over 10 seconds | Upper or lower |

Averages, maximums, normalized power, and intensity factor belong to the activity you are recording. They start over each time you begin an activity. Lap values belong to the lap in progress, or to the lap you just finished. Smoothed values keep a rolling window, so a 20-minute or 1-hour power needs that much riding before it reflects a full window.

Torque and pedal balance appear only when the power meter sends them. A single-sided meter often leaves those rows at `--`.

## Before you start

- A current-generation Karoo, on software **1.538.2049** or later. Installing from a phone is available on that generation.
- The **Hammerhead Companion** app on your phone: Android **1.36.0** or later, or iPhone **1.12.0** or later.
- The phone online, and the Karoo turned on, signed in to the same account, and connected to Wi-Fi.
- The PowerView release: [github.com/eduper11/powerview/releases](https://github.com/eduper11/powerview/releases). The file on that release is **PowerView.apk**.
- An ANT+ or Bluetooth power meter. With no sensor paired, the field stays blank.

## 1. Install PowerView on the Karoo

The app is installed from the **PowerView.apk** link on the release. The phone shows a transfer screen, then the Karoo opens an install screen. Tap **Install** on the Karoo to finish. The field is on the device once you accept it there.

1. On your phone's browser, open the release link.
2. On that release, long-press **PowerView.apk**.
3. In the share menu, choose **Hammerhead Companion**.
4. Wait for the Karoo to show the install screen, then tap **Install**.

The Karoo needs to be on and connected to Wi-Fi while the APK transfers.

### Check that it installed

On the Karoo, open the extension library. PowerView should appear among the third-party apps.

Open the PowerView app from that library. The screen **PowerView — Configuración** is where you choose the three values. The defaults are already set: large is **Potencia (3s)**, upper is **Potencia máxima (actividad)**, and lower is **Potencia media (actividad)**.

When a new release is published, install it the same way: open the release link, long-press **PowerView.apk**, and share it with Hammerhead Companion.

## 2. Pair the power meter

The field reads the power the Karoo receives from the sensor. If the sensor is not paired, or is not enabled on the profile you ride with, you will see `--` and a white background for the whole ride.

1. Wake the power meter. Pedal a few strokes, or spin the crank, so it starts broadcasting.
2. On the Karoo home screen, tap **Apps**.
3. Open **Sensors**.
4. Tap **+** to search for nearby sensors. You can filter by ANT+ or Bluetooth.
5. Select your power meter and confirm with **✓**. The Karoo shows a green screen when pairing succeeds.

After that, the sensor reconnects on its own whenever it is active. You do not need to pair it before every ride.

If you use more than one profile, assign it to the profile you will use with PowerView:

1. In **Sensors**, open the power meter.
2. Scroll down to **Enabled on**.
3. Tap **Select** and check that profile.

Or, from the profile:

1. **Apps** → **Profiles**.
2. Open the profile.
3. Scroll down to **Sensors Enabled**, tap **Select**, and check the power meter.

## 3. Set your FTP, weight, and power zones

The field color is the zone the Karoo calculates from your FTP and these thresholds. If the zones are not yours, the power number can be right while the color is wrong. **% FTP** uses the same FTP. **W/KG** uses your weight.

1. On the home screen, tap **Apps**.
2. Open **Settings**.
3. Scroll down to **Training Metrics**.
4. Open **Power Zones**.
5. Enter your FTP and review the boundaries of the seven zones. You can keep the Karoo's automatic calculation, or type the boundaries yourself, using the same values you train with.
6. In the same Training Metrics area, check that your weight is the weight you want **W/KG** to use.

You can also edit weight, heart-rate zones, and power zones from the Hammerhead Dashboard, under Training Metrics settings. If you connect TrainingPeaks, Strava, intervals.icu, or another app, those zones can sync to your Hammerhead account. On the Karoo, check that the FTP and the boundaries that arrived are the ones you want to see on the road: each platform calculates zones differently, and the PowerView color always follows the Karoo.

Changing the zones in Training Metrics changes the field color on the next reading.

## 4. Choose the three values

1. On the Karoo, open the extension library and open **PowerView**.
2. On **Valor grande**, pick the large number. The list is only **Potencia actual**, **Potencia (3s)**, **Potencia (5s)**, and **Potencia (10s)**.
3. On **Superior**, pick the upper-right metric, or **Sin usar** to leave that corner empty.
4. On **Inferior**, pick the lower-right metric, or **Sin usar** to leave that corner empty.

Leave the app. The data page uses this choice the next time it draws the field. You can change it again before a ride or while one is in progress.

## 5. Add the field to a page

1. **Apps** → **Profiles**.
2. Open the profile you ride with. Check that the power meter is still listed under **Sensors Enabled**.
3. Open that profile's data pages.
4. Add a page with **+** (a data page or a map page), or edit one you already have.
5. Add a field and search for **⚡ PowerView**. The filters at the top of the list narrow the search.
6. Select it. In the editor, the field cycles through the seven zones with sample watts so you can see the colors before you ride. Those sample numbers are not your live power. The short labels in the preview are the ones you chose in the PowerView app.
7. Long-press the field and drag it to the place on the page where you want to read it. On a map page you can put the fields at the top or the bottom.
8. Leave the editor to save the page.

The large number grows with the cell. A wide cell is easy to read at a glance; a half-width cell holds the same data, smaller. Each page holds up to 10 fields, and each profile holds up to 12 pages.

To remove it, tap the field in the editor and choose delete. During a ride you can also replace it: long-press the field and pick another one. While riding you can change the field, and the page layout stays as you saved it.

## 6. Use it on a ride

1. Choose the profile where you added **⚡ PowerView**.
2. Check that the power meter is on and connected.
3. Press record to start the activity.

From then on:

- The large number follows the metric you chose.
- The upper and lower numbers follow their metrics for this recording. Activity averages and maximums start over each time you begin an activity.
- The background changes zone when your power crosses the boundaries you set.

If you pause, the field keeps the last power, the smaller values, and the color. When you stop the activity and return to idle, the field goes back to `--` on a white background. That is expected: outside a recording there is no ride average or maximum to show.

## If something looks wrong

| What you see | What to check |
| --- | --- |
| The field does not appear when you add data | Open the extension library and confirm PowerView is installed. If the install stopped on the phone, accept **Install** on the Karoo. |
| `--` and a white background before you press record | This is the idle state. Start the activity. |
| `--` and a white background while recording | The power meter is not connected, or it is not checked under **Sensors Enabled** for this profile. Open it in **Sensors** and confirm it is on the profile. Wake it by pedaling. |
| Power changes and the color is not what you expect | The **Power Zones** boundaries and the FTP in Training Metrics. PowerView colors the zone the Karoo reports. |
| The upper or lower corner is missing | That row is set to **Sin usar** in the PowerView app. Pick a metric on **Superior** or **Inferior**. |
| A small value stays at `--` | Torque and pedal balance need a meter that sends them. Lap values for the previous lap stay at `--` until you finish a lap. A long smoothed window stays thin until you have been riding through that window. |
| **W/KG** or **%FTP** does not match what you expect | Your weight and your FTP in Training Metrics. PowerView uses the numbers on the Karoo. |
| An average or a maximum does not match another screen | Those values belong to the current activity, from the moment you pressed record. A previous activity does not carry over into the next recording. |
| The numbers in the page editor keep changing color | That is the preview. It cycles sample watts through the seven zones. Live power starts when you record a ride. |
| The release never starts installing | The Karoo needs to be on Wi-Fi. Long-press **PowerView.apk** on the release, and share that link with Hammerhead Companion. |
